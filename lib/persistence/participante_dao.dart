import '../core/database.dart';
import '../models/participante.dart';

class ParticipanteDao {
  static const String table = 'participante';

  Future<int> insertParticipante(Participante participante) async {
    final db = await AppDatabase().database;
    return db.insert(table, participante.toMap());
  }

  Future<List<Participante>> getAllParticipantes() async {
    final db = await AppDatabase().database;
    final result = await db.query(table);
    return result.map((map) => Participante.fromMap(map)).toList();
  }

  Future<List<Participante>> buscaParticipantes(String nomeParte) async {
    final db = await AppDatabase().database;
    final result = await db.rawQuery("SELECT * FROM participante WHERE nome LIKE ?", ['%$nomeParte%']);
    print(result);
    return result.map((map) => Participante.fromMap(map)).toList();
  }
}
