import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:projetomobile/models/contribuicao.dart';
import 'package:projetomobile/pages/add_contribuicao_page.dart';
import 'package:projetomobile/service/contribuicao_service.dart';

class ViewContribuicoesPage extends StatefulWidget {
  const ViewContribuicoesPage({super.key});

  @override
  _ViewContribuicoesPageState createState() => _ViewContribuicoesPageState();
}

class _ViewContribuicoesPageState extends State<ViewContribuicoesPage> {
  final ContribuicaoService contribuicaoService = ContribuicaoService();
  late Future<List<Contribuicao>> _contribuintes;

  @override
  void initState() {
    super.initState();
    _contribuintes = contribuicaoService.getContribuicoes();
  }

  void _refreshContribuintes() {
    setState(() {
      _contribuintes = contribuicaoService.getContribuicoes();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Contribuições')),
      body: FutureBuilder<List<Contribuicao>>(
        future: _contribuintes,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Center(child: Text('Erro: ${snapshot.error}'));
          }

          final contribuicoes = snapshot.data ?? [];

          if (contribuicoes.isEmpty) {
            return Center(child: Text('Nenhuma contribuição encontrada.'));
          }

          return ListView.builder(
            itemCount: contribuicoes.length,
            itemBuilder: (context, index) {
              final contribuicao = contribuicoes[index];
              return ListTile(
                leading: Icon(Icons.money),
                title: Text(contribuicao.nomeParticipante),
                subtitle: Text(
                    '${contribuicao.dataPagamento} • R\$ ${contribuicao.valor.toStringAsFixed(2)}'),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const AddContribuicaoPage()),
          );
          _refreshContribuintes();
        },
        child: const Icon(Icons.add),
      ),
    );
  }

}