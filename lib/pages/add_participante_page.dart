import 'package:flutter/material.dart';
import '../models/participante.dart';
import '../service/participante_service.dart';

class AddParticipantePage extends StatefulWidget {
  const AddParticipantePage({super.key});

  @override
  _AddParticipantePageState createState() => _AddParticipantePageState();
}

class _AddParticipantePageState extends State<AddParticipantePage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _ocupacaoController = TextEditingController();
  final ParticipanteService _ParticipanteService = ParticipanteService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Adicionar participante')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(labelText: 'Nome'),
                validator: (value) => value!.isEmpty ? 'Campo obrigatório' : null,
              ),
              TextFormField(
                controller: _ocupacaoController,
                decoration: const InputDecoration(labelText: 'Ocupação'),
                validator: (value) => value!.isEmpty ? 'Campo obrigatório' : null,
              ),
              ElevatedButton(
                onPressed: _saveParticipante,
                child: const Text('Salvar'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _saveParticipante() async {
    if (_formKey.currentState!.validate()) {
      final participante = Participante(
        nome: _nameController.text,
        ocupacao: _ocupacaoController.text,
      );
      await _ParticipanteService.addParticipante(participante);
      Navigator.pop(context);
    }
  }
}
