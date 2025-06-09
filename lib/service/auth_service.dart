import '../persistence/user_dao.dart';
import '../models/user.dart';

class AuthService {
  final UserDao _userDao = UserDao();

  Future<bool> register(User user) async {
    try {
      await _userDao.insertUser(user);
      return true;
    } catch (e) {
      return false;
    }
  }

  Future<User?> login(String email, String password) async {
    return await _userDao.getUser(email, password);
  }
}
