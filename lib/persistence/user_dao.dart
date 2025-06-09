import '../models/user.dart';
import '../core/database.dart';

class UserDao {
  static const String table = 'usuario';

  Future<int> insertUser(User user) async {
    final db = await AppDatabase().database;
    return db.insert(table, user.toMap());
  }

  Future<User?> getUser(String email, String password) async {
    final db = await AppDatabase().database;
    final result = await db.query(
      table,
      where: 'email = ? AND password = ?',
      whereArgs: [email, password],
    );
    return result.isNotEmpty ? User.fromMap(result.first) : null;
  }
}
