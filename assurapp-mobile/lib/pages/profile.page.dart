import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import '../widgets/sidebar.widget.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({super.key});

  @override
  State<StatefulWidget> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  late Map<String, dynamic> user;
  TextEditingController firstnameController = TextEditingController();
  TextEditingController lastnameController = TextEditingController();
  TextEditingController nissController = TextEditingController();
  TextEditingController streetController = TextEditingController();
  TextEditingController cityController = TextEditingController();
  TextEditingController countryController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController phoneController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController passwordCController = TextEditingController();
  bool visible = true;

  void toggleVisible() {
    setState(() {
      visible = !visible;
    });
  }

  @override
  void initState() {
    super.initState();
    redirect();
    fill();
  }

  Future<void> redirect() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? token = prefs.getString('token');
    if (token == null) Navigator.pushNamedAndRemoveUntil(context, "/login", (route) => false);
  }

  Future<void> fill() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    int? id = prefs.getInt('id');
    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/users/$id');
    final response = await http.get(uri);
    if (response.statusCode == 200) {
      user = json.decode(response.body);

      firstnameController.text = user['name'];
      lastnameController.text = user['lastname'];
      nissController.text = user['legalId'] ?? '';
      streetController.text = user['address'].split('%')[0] ?? '';
      cityController.text = user['address'].split('%')[1] ?? '';
      countryController.text = user['address'].split('%')[2] ?? '';
      emailController.text = user['email'];
      phoneController.text = user['phoneNumber'] ?? '';
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
      );
    }
  }

  Future<void> update() async {
    user['name'] = firstnameController.text;
    user['lastname'] = lastnameController.text;
    user['legalId'] = nissController.text;
    user['address'] = '${streetController.text}%${cityController.text}%${countryController.text}';
    user['username'] = emailController.text;
    user['phoneNumber'] = phoneController.text;
    if (passwordController.text == passwordCController.text) user['password'] = passwordController.text;

    final Uri uri = Uri.parse('http://127.0.0.1:8000/api/v1/usersUpdate');

    final String data = json.encode(user);
    final response = await http.put(uri, headers: {'Content-Type': 'application/json',}, body: data,);

    if (response.statusCode == 200) {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Update with Success"), backgroundColor: Colors.green,)
      );
      Navigator.pushNamedAndRemoveUntil(context, "/", (route) => false);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("An error occurred please check your data or network."), backgroundColor: Colors.red,)
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer:  const Sidebar(),
      appBar: AppBar(title: const Text("Profile"),),
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
                            controller: firstnameController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.person_outline_outlined),
                                labelText: "First Name",
                                hintText: "John",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: lastnameController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.person_outline_outlined),
                                labelText: "Last Name",
                                hintText: "Doe",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: nissController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.person_outline_outlined),
                                labelText: "NISS",
                                hintText: "0000-0000-000-00",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: streetController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.house_outlined),
                                labelText: "Street",
                                hintText: "1234 Elm Street Citytown",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: cityController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.house_outlined),
                                labelText: "City",
                                hintText: "Stateville 56789",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: countryController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.house_outlined),
                                labelText: "Contry",
                                hintText: "Countryland",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: emailController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.alternate_email_outlined),
                                labelText: "Email",
                                hintText: "expert@example.com",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: phoneController,
                            decoration: const InputDecoration(
                                prefixIcon: Icon(Icons.phone_android_outlined),
                                labelText: "Phone",
                                hintText: "0032 000 000000",
                                border: OutlineInputBorder()
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: passwordController,
                            obscureText: visible,
                            decoration: InputDecoration(
                                prefixIcon: const Icon(Icons.fingerprint),
                                labelText: "Password",
                                hintText: "************",
                                border: const OutlineInputBorder(),
                                suffixIcon: IconButton(
                                  icon: Icon(visible ? Icons.remove_red_eye_sharp: Icons.remove_red_eye_outlined),
                                  onPressed: toggleVisible,
                                )
                            ),
                          ),
                          const SizedBox(height: 10,),
                          TextFormField(
                            controller: passwordCController,
                            obscureText: true,
                            decoration: const InputDecoration(
                              prefixIcon: Icon(Icons.fingerprint),
                              labelText: "Confirm Password",
                              hintText: "************",
                              border: OutlineInputBorder(),
                            ),
                          ),
                          const SizedBox(height: 10,),
                          SizedBox(
                            width: double.infinity,
                            child:  FilledButton(
                              onPressed: () => {update()},
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