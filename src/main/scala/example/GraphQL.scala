package example

import caliban.GraphQL.graphQL
import caliban._
import caliban.schema._
import caliban.execution.QueryExecution
import caliban.interop.tapir.TapirAdapter
import sttp.tapir.json.circe._
import zio._

class GraphQL extends GenericSchema[Any] {

  def createServerEndpoints =
    createInterpreter.map(i =>
      TapirAdapter.makeHttpService(
        interpreter = i,
        skipValidation = false,
        enableIntrospection = true,
        queryExecution = QueryExecution.Parallel
      )
    )

  case class Character(name: String)

  case class Queries(
      @Annotations.GQLDescription("Return all characters from a given origin")
      characters: () => Task[List[Character]])

  case class CharacterArgs(name: String)
  case class Mutations(deleteCharacter: CharacterArgs => Task[Boolean])

  implicit val characterSchema: Schema[Any, Character] = obj("Character")(implicit o =>
    List(
      field("name")(_.name),
      field("name")(_.name)
    )
  )

  def createInterpreter: ZIO[Any, CalibanError.ValidationError, GraphQLInterpreter[Any, CalibanError]] =
    for {
      i <- graphQL[Any, Queries, Mutations, Unit](
        RootResolver(
          Queries(() => ZIO.succeed(List(Character("jay")))),
          Mutations(args =>
            ZIO.logWarning(s">>>> $args") <*> ZIO.succeed {
              args.name == "jay"
            }
          )
        )
      ).interpreter
    } yield i
}
