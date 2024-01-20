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


class ExpertisePage extends StatefulWidget {
  const ExpertisePage({super.key});

  @override
  State<StatefulWidget> createState() => _ExpertisePageState();

}

class _ExpertisePageState extends State<ExpertisePage> {

  TextEditingController dateController = TextEditingController(text: DateTime.now().toString());
  TextEditingController descriptionController = TextEditingController();
  TextEditingController estimationController = TextEditingController();

  XFile? _image;

  @override
  void initState() {
    super.initState();
  }

  Future<void> _takePicture() async {
    final XFile? picture = await ImagePicker().pickImage(source: ImageSource.camera);
    setState(() {
      _image = picture;
    });
  }

  Future<void> add() async {
    var uuid = const Uuid();
    var uid = uuid.v1();
    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/expertises');

    try {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      int? claimID = prefs.getInt('claim');
      int? id = prefs.getInt('id');
      String? token = prefs.getString('token');

      final expert = await http.get(Uri.parse('http://127.0.0.1:8000/api/v1/users/$id'), headers: {"Authorization": "Bearer $token"});
      final claim = await http.get(Uri.parse('http://127.0.0.1:8000/api/v1/claims/$claimID'), headers: {"Authorization": "Bearer $token"});

      final ref = FirebaseStorage.instance.ref().child("$uid.${_image!.path.split('.').last}");

      final snapshot = await ref.putFile(File(_image!.path), SettableMetadata(contentType: 'image/${_image!.path.split('.').last}'),);
      final path = await snapshot.ref.getDownloadURL();


      String data = jsonEncode({
        'id': 0,
        'description': descriptionController.text,
        'date': dateController.text.split(' ')[0],
        'estimation': estimationController.text,
        'imageFile': path.toString(),
        'claim': jsonDecode(claim.body),
        'expert': jsonDecode(expert.body)
      });

      var response = await http.post(uri, body: data, headers: {'Content-Type': 'application/json',"Authorization": "Bearer $token"});

      if (response.statusCode == 200) {
        Map<String, dynamic> c = jsonDecode(claim.body);
        c['status'] = 'PROGRESS';
        await http.put(Uri.parse('http://127.0.0.1:8000/api/v1/claims'), body: json.encode(c), headers: {'Content-Type': 'application/json',"Authorization": "Bearer $token"});
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("Added with success."), backgroundColor: Colors.green,)
        );
        Navigator.pushNamedAndRemoveUntil(context, '/expertises', (route) => false);
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
        );
      }

    } catch (e) {
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
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.money_off_outlined),
                                labelText: "Estimation",
                                hintText: "1200.00",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          if (_image != null) Image.file(File(_image!.path), width: double.infinity),
                          const SizedBox(height: 10,),
                          FilledButton(
                            onPressed: _takePicture,
                            style: ButtonStyle(
                                padding: MaterialStateProperty.all<EdgeInsets>(
                                    const EdgeInsets.all(18)
                                ),
                                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                    const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(5)),)
                                )
                            ),
                            child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                              const Icon(Icons.camera_alt_outlined),
                              Text("\tTake Picture".toUpperCase())
                            ]),
                          ),
                          const SizedBox(height: 10,),
                          SizedBox(
                            width: double.infinity,
                            child:  FilledButton(
                              onPressed: () => add(),
                              style: ButtonStyle(
                                  padding: MaterialStateProperty.all<EdgeInsets>(
                                      const EdgeInsets.all(18)
                                  ),
                                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                      const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(5)),)
                                  )
                              ),
                              child: Text("Update".toUpperCase()),
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