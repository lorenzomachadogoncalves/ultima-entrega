import 'package:projetomobile/models/contribuicao.dart';
import 'package:projetomobile/persistence/contribuicao_dao.dart';

class ContribuicaoService {
  final ContribuicaoDao _contribuicaoDao = ContribuicaoDao();

  Future<int> addContribuicao(Contribuicao contribuicao) async {
    return await _contribuicaoDao.insertContribuicao(contribuicao);
  }

  Future<List<Contribuicao>> getContribuicoes() async {
    return await _contribuicaoDao.getAllContribuicao();
  }

}