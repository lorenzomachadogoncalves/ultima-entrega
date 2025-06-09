import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class AppDatabase {
  static final AppDatabase _instance = AppDatabase._internal();
  static Database? _database;

  factory AppDatabase() => _instance;

  AppDatabase._internal();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDatabase();
    return _database!;
  }

  Future<Database> _initDatabase() async {
    final path = join(await getDatabasesPath(), 'acdv.db');
    return openDatabase(
      path,
      version: 1,
      onCreate: (db, version) async {
        await db.execute('''
          CREATE TABLE usuario(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT,
            email TEXT,
            password TEXT
          )
        ''');
        await db.execute('''
          CREATE TABLE participante(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT,
            ocupacao TEXT
          )
        ''');
        await db.execute('''
          CREATE TABLE contribuicao(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            idParticipante INTEGER,
            nomeParticipante TEXT,
            dataPagamento TEXT,
            valor NUMERIC,
            FOREIGN KEY (idParticipante) REFERENCES participante(id)
          )
        ''');
      },
    );
  }
}
