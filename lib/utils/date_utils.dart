import 'package:intl/intl.dart';

class DateUtilsHelper {
  static String formataData(DateTime date) {
    return DateFormat('dd/MM/yyyy').format(date);
  }
}