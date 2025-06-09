import 'package:flutter/material.dart';
import 'package:flutter_multi_formatter/flutter_multi_formatter.dart';
import 'package:flutter_typeahead/flutter_typeahead.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';
import 'package:projetomobile/models/participante.dart';
import 'package:projetomobile/service/participante_service.dart';
import '../models/contribuicao.dart';
import '../service/contribuicao_service.dart';

class AddContribuicaoPage extends StatefulWidget {
  const AddContribuicaoPage({super.key});

  @override
  _AddContribuicaoPageState createState() => _AddContribuicaoPageState();
}

class _AddContribuicaoPageState extends State<AddContribuicaoPage> {

  final _formKey = GlobalKey<FormState>();

  final TextEditingController _participanteController = TextEditingController();
  final TextEditingController _dataController = TextEditingController();
  final TextEditingController _valorController = TextEditingController();

  final ParticipanteService _participanteService = ParticipanteService();
  final ContribuicaoService _contribuicaoService = ContribuicaoService();

  final MaskTextInputFormatter dataFormatter = MaskTextInputFormatter(
    mask: '##/##/####',
    filter: { '#' : RegExp(r'[0-9]')},
    type: MaskAutoCompletionType.lazy,
  );

  final CurrencyInputFormatter dinheiroFormatter = CurrencyInputFormatter(
    leadingSymbol: 'R\$',
    useSymbolPadding: true,
    thousandSeparator: ThousandSeparator.Period,
    mantissaLength: 2,
  );

  Participante? _participanteSugerido;
  int? _idParticipanteSugerido;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Cadastrar contribuicao')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            children: [
              TypeAheadFormField<Participante>(
                  textFieldConfiguration: TextFieldConfiguration(
                    controller: _participanteController,
                    decoration: const InputDecoration(labelText: 'Participante')
                  ),
                  suggestionsCallback: (nome) async {
                    return await _participanteService.buscaParticipantes(nome);
                  },
                  itemBuilder: (context, Participante sugestao) {
                    return ListTile(
                      title: Text(sugestao.nome),
                    );
                  },
                  onSuggestionSelected: (Participante sugestao) {
                    _participanteController.text = sugestao.nome;
                    _participanteSugerido = sugestao;
                    _idParticipanteSugerido = sugestao.id;
                  },
                validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Campo obrigatório';
                    }

                    if (_participanteSugerido == null) {
                      return 'Selecione um participante da lista';
                    }

                    return null;
                },
              ),
              TextFormField(
                controller: _dataController,
                inputFormatters: [dataFormatter],
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(labelText: 'Data da contribuição'),
                validator: (value) => value!.isEmpty ? 'Campo obrigatório' : null,
              ),
              TextFormField(
                controller: _valorController,
                inputFormatters: [dinheiroFormatter],
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                    labelText: 'Valor da contribuição',
                ),
                validator: (value) {
                  if (value == null || value.isEmpty || value == 'R\$ 0,00') {
                    return 'Informe um valor';
                  }
                }
              ),
              ElevatedButton(
                onPressed: _saveContribuicao,
                child: const Text('Salvar'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _saveContribuicao() async {
    String valorFormatado = toNumericString(_valorController.text);

    if (_formKey.currentState!.validate()) {
      final contribuicao = Contribuicao(
        idParticipante: _idParticipanteSugerido,
        nomeParticipante: _participanteController.text,
        valor: double.parse(valorFormatado) / 100,
        dataPagamento: _dataController.text,
      );
      await _contribuicaoService.addContribuicao(contribuicao);
      Navigator.pop(context);
    }
  }
}
