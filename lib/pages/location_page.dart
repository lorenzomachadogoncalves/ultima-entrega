import 'package:flutter/material.dart';
import 'package:location/location.dart';

class LocationPage extends StatefulWidget {
  @override
  State<LocationPage> createState() => _LocationPageState();
}

class _LocationPageState extends State<LocationPage> {
  final Location location = Location();
  LocationData? _currentLocation;
  bool _permissionGranted = false;
  String _locationText = "Localização ainda não obtida.";
  late bool _serviceEnabled;
  late PermissionStatus _permissionStatus;

  @override
  void initState() {
    super.initState();
    _initializeLocation();
  }

  Future<void> _initializeLocation() async {
    //await location.enableBackgroundMode(enable: true);
    _serviceEnabled = await location.serviceEnabled();
    if (!_serviceEnabled) {
      _serviceEnabled = await location.requestService();
      if (!_serviceEnabled) {
        setState(() {
          _locationText = "Serviço de localização desativado.";
        });
        return;
      }
    }

    _permissionStatus = await location.hasPermission();
    if (_permissionStatus == PermissionStatus.denied) {
      _permissionStatus = await location.requestPermission();
      if (_permissionStatus != PermissionStatus.granted) {
        setState(() {
          _locationText = "Permissão de localização negada.";
        });
        return;
      }
    }

    setState(() {
      _permissionGranted = true;
    });

    _currentLocation = await location.getLocation();
    _updateLocationText(_currentLocation!);

    // Escutar mudanças
    location.onLocationChanged.listen((LocationData newLocation) {
      setState(() {
        _currentLocation = newLocation;
        _updateLocationText(newLocation);
      });
    });
  }

  void _updateLocationText(LocationData locationData) {
    _locationText = 'Latitude: ${locationData.latitude}, Longitude: ${locationData.longitude}';
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Exemplo com Location'),
      ),
      body: Center(
        child: Text(
          _locationText,
          style: TextStyle(fontSize: 20),
          textAlign: TextAlign.center,
        ),
      ),
    );
  }
}
