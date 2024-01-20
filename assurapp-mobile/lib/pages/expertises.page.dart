import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;

import '../widgets/sidebar.widget.dart';


class ExpertisesPage extends StatefulWidget {
  const ExpertisesPage({super.key});

  @override
  State<StatefulWidget> createState() => _ExpertisesPageState();

}

class _ExpertisesPageState extends State<ExpertisesPage> {
  List<dynamic> expertises = [];

  @override
  void initState() {
    super.initState();
    fetch();
  }


  Future<void> fetch() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int? id = prefs.getInt('id');
    String? token = prefs.getString('token');
    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/expertises/expert/$id');
    final response = await http.get(uri, headers: {"Authorization": "Bearer $token"});
    if (response.statusCode == 200) {
      expertises = json.decode(response.body);
    } else {
      print(response.body);
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
      );
    }
  }

  Future<void> add(id) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setInt('expertise', id);
    await Navigator.pushNamed(context, '/expertise-view');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        drawer:  const Sidebar(),
        appBar: AppBar(title: const Text("Expertises"),),
        body: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: FutureBuilder(
            future: fetch(),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return const CircularProgressIndicator();
              } else if (snapshot.hasError) {
                return Text('Error: ${snapshot.error}');
              } else {
                return ListView.builder(
                  itemCount: expertises.length,
                  itemBuilder: (context, index) => Card(
                    child: ListTile(
                      title: Text(expertises[index]['description']),
                      subtitle: Text(expertises[index]['date']),
                      onTap: ()  =>  add(expertises[index]['id']),
                    ),
                  ),
                );
              }
            },
          ),
        )
    );
  }
}