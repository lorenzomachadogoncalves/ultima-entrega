import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:projetomobile/pages/view_contribuicoes.dart';
import 'package:projetomobile/pages/view_participantes.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Início'),),
      body: Center(
        child : Padding(padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              FilledButton.tonal(
                onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const ViewParticipantesPage()
                  ),
                ),
                child: const Text('Participantes'),
              ),
              FilledButton.tonal(
                  onPressed: () => Navigator.push(
                      context,
                      MaterialPageRoute(
                          builder: (context) => const ViewContribuicoesPage()
                      )
                  ),
                  child: const Text('Contribuições')
              ),
            ],
          ),
        ),
      ),
    );
  }
}