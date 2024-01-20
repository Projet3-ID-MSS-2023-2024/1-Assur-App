import 'dart:convert';
import 'dart:io';

import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:http_parser/http_parser.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'package:uuid/uuid.dart';


class ExpertiseShowPage extends StatefulWidget {
  const ExpertiseShowPage({super.key});

  @override
  State<StatefulWidget> createState() => _ExpertisePageState();

}

class _ExpertisePageState extends State<ExpertiseShowPage> {

  TextEditingController dateController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();
  TextEditingController estimationController = TextEditingController();
  String _link = "";

  @override
  void initState() {
    super.initState();
    _fill();
  }

  Future<void> _fill() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int? expertise = prefs.getInt('expertise');
    String? token = prefs.getString('token');

    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/expertises/$expertise');
    final response = await http.get(uri, headers: {'Content-Type': 'application/json', 'Authorization': 'Bearer $token'});

    if (response.statusCode == 200) {
      Map<String, dynamic> expert = json.decode(response.body);

      dateController.text = expert['date'];
      descriptionController.text = expert['description'];
      estimationController.text = expert['estimation'].toString() ?? '';
      _link = expert['imageFile'].toString() ?? '';

      setState(() {});
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
      );
    }

  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Expertise"),),
      body:  SafeArea(
        child: SingleChildScrollView(
          child: Container(
            padding: const EdgeInsets.all(10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Form(
                    child: Container(
                      padding: const EdgeInsets.symmetric(vertical: 10),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          TextFormField(
                            controller: dateController,
                            enabled: false,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.calendar_month_outlined),
                                labelText: "Date",
                                hintText: "2000-01-28",
                                border: OutlineInputBorder(),
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: descriptionController,
                            enabled: false,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.notes_outlined),
                                labelText: "Description",
                                hintText: "Damages ...",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: estimationController,
                            enabled: false,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.money_off_outlined),
                                labelText: "Estimation",
                                hintText: "1200.00",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          Center(
                            child: Image.network(
                              _link,
                              loadingBuilder: (BuildContext context, Widget child, ImageChunkEvent? loadingProgress) {
                                if (loadingProgress == null) {
                                  return child;
                                } else {
                                  return Center(
                                    child: CircularProgressIndicator(
                                      value: loadingProgress.expectedTotalBytes != null
                                          ? loadingProgress.cumulativeBytesLoaded / (loadingProgress.expectedTotalBytes ?? 1)
                                          : null,
                                    ),
                                  );
                                }
                              },
                              errorBuilder: (BuildContext context, Object error, StackTrace? stackTrace) {
                                return const Text('Error loading image');
                              },
                            ),
                          )
                        ],
                      ),
                    )
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}