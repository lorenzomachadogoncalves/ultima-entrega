import '../persistence/participante_dao.dart';
import '../models/participante.dart';

class ParticipanteService {
  final ParticipanteDao _participanteDao = ParticipanteDao();

  Future<int> addParticipante(Participante participante) async {
    return await _participanteDao.insertParticipante(participante);
  }

  Future<List<Participante>> getParticipantes() async {
    return await _participanteDao.getAllParticipantes();
  }

  Future<List<Participante>> buscaParticipantes(String nomeParte) async {
    return await _participanteDao.buscaParticipantes(nomeParte);
  }
}
