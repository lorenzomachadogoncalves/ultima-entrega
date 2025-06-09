class Participante {
  final int? id;
  final String nome;
  final String ocupacao;

  Participante({
    this.id,
    required this.nome,
    required this.ocupacao,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'nome': nome,
      'ocupacao': ocupacao,
    };
  }

  factory Participante.fromMap(Map<String, dynamic> map) {
    return Participante(
      id: map['id'],
      nome: map['nome'],
      ocupacao: map['ocupacao'],
    );
  }
}
