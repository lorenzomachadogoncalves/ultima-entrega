import 'package:projetomobile/core/database.dart';
import 'package:projetomobile/models/contribuicao.dart';

class ContribuicaoDao {
  static const String table = 'contribuicao';

  Future<int> insertContribuicao(Contribuicao contribuicao) async {
    final db = await AppDatabase().database;
    return db.insert(table, contribuicao.toMap());
  }

  Future<List<Contribuicao>> getAllContribuicao() async {
    final db = await AppDatabase().database;
    final result = await db.query(table);
    return result.map((map) => Contribuicao.fromMap(map)).toList();
  }

}