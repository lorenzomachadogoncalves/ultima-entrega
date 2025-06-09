class Contribuicao {
  final int? id;
  final int? idParticipante;
  final String nomeParticipante;
  final double valor;
  final String dataPagamento;

  Contribuicao({
    this.id,
    required this.idParticipante,
    required this.nomeParticipante,
    required this.valor,
    required this.dataPagamento
});

  Map<String, dynamic> toMap() {
    return {
      'id' : id,
      'idParticipante' : idParticipante,
      'nomeParticipante' : nomeParticipante,
      'valor' : valor,
      'dataPagamento' : dataPagamento
    };
  }

  factory Contribuicao.fromMap(Map<String, dynamic> map) {
    return Contribuicao(
      id: map['id'],
      idParticipante: map['idParticipante'],
      nomeParticipante: map['nomeParticipante'] ?? '',
      valor: map['valor'],
      dataPagamento: map['dataPagamento']
    );
  }
}