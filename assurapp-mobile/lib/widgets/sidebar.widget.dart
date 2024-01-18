import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Sidebar extends StatelessWidget {
  const Sidebar({super.key});

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        children: [
          const DrawerHeader(padding: EdgeInsets.all(30),child: Center(child: Image(image: AssetImage("assets/images/icon.png"),)),),
          ListTile(title: const Text("Profile"), leading: const Icon(Icons.person_outline), trailing: const Icon(Icons.arrow_right), onTap: () => Navigator.pushNamed(context, '/'),),
          ListTile(title: const Text("Claims"), leading: const Icon(Icons.list_outlined), trailing: const Icon(Icons.arrow_right), onTap: () => Navigator.pushNamed(context, '/claims'),),
          ListTile(title: const Text("Expertises"), leading: const Icon(Icons.edit_note_outlined), trailing: const Icon(Icons.arrow_right), onTap: () => Navigator.pushNamed(context, '/expertises'),),
          ListTile(title: const Text("Logout"), leading: const Icon(Icons.exit_to_app_outlined), trailing: const Icon(Icons.arrow_right), onTap: () => clearSharedPreferences(context),),
        ],
      ),
    );
  }

  Future<void> clearSharedPreferences(BuildContext context) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    await prefs.clear();
    Navigator.pushNamedAndRemoveUntil(context, "/login", (route) => false);
  }
}