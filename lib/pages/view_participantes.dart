import 'package:flutter/material.dart';
import '../service/participante_service.dart';
import 'add_participante_page.dart';
import '../models/participante.dart';

class ViewParticipantesPage extends StatefulWidget {
  const ViewParticipantesPage({super.key});

  @override
  _ViewParticipantesPageState createState() => _ViewParticipantesPageState();
}

class _ViewParticipantesPageState extends State<ViewParticipantesPage> {
  final ParticipanteService _participanteService = ParticipanteService();
  late Future<List<Participante>> _participantes;

  @override
  void initState() {
    super.initState();
    _participantes = _participanteService.getParticipantes();
  }

  void _refreshParticipantes() {
    setState(() {
      _participantes = _participanteService.getParticipantes();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Participantes')),
      body: FutureBuilder<List<Participante>>(
        future: _participantes,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          if (snapshot.hasError) {
            return Center(child: Text('Erro: ${snapshot.error}'));
          }

          final participantes = snapshot.data ?? [];

          if (participantes.isEmpty) {
            return Center(child: Text('Nenhum participante encontrado.'));
          }
          return ListView.builder(
            itemCount: snapshot.data!.length,
            itemBuilder: (context, index) {
              final participante = snapshot.data![index];
              return ListTile(
                title: Text(participante.nome),
                subtitle: Text(participante.ocupacao),
              );
            },
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const AddParticipantePage()),
          );
          _refreshParticipantes();
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}
