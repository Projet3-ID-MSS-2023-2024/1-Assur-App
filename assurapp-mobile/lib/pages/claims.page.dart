import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;

import '../widgets/sidebar.widget.dart';


class ClaimsPage extends StatefulWidget {
  const ClaimsPage({super.key});

  @override
  State<StatefulWidget> createState() => _ClaimsPageState();

}

class _ClaimsPageState extends State<ClaimsPage> {
  List<dynamic> claims = [];

  @override
  void initState() {
    super.initState();
    fetch();
  }


  Future<void> fetch() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int? id = prefs.getInt('id');
    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/claims/expert/$id');
    final response = await http.get(uri);
    if (response.statusCode == 200) {
      claims = json.decode(response.body);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
      );
    }
  }

  Future<void> add(id) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.setInt('claim', id);
    await Navigator.pushNamed(context, '/expertise');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer:  const Sidebar(),
      appBar: AppBar(title: const Text("Claims"),),
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
                itemCount: claims.length,
                itemBuilder: (context, index) => Card(
                  child: ListTile(
                    title: Text(claims[index]['description']),
                    subtitle: Text(claims[index]['date']),
                    trailing: claims[index]['status'] == 'PENDING' ?  const Text("Expertise") : null,
                    onTap: ()  =>  claims[index]['status'] == 'PENDING' ?  add(claims[index]['id']) : null,
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