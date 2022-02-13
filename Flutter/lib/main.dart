import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:notification_proj/red_page.dart';
import 'package:notification_proj/services/local_notification_service.dart';
import 'green_page.dart';

//receive message when app is in background
Future<void> backgroundHandler(RemoteMessage message) async {
  print(message.data.toString());
  print(message.notification!.title);
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  FirebaseMessaging.onBackgroundMessage(backgroundHandler);
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Notification Learning',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Notification Learning Home Page'),
      routes: {
        "red": (_) => RedPage(),
        "green": (_) => GreenPage(),
      },
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {


  _register() {
    FirebaseMessaging.instance.getToken().then((token) => print(token));
  }

  @override
  void initState() {
    super.initState();
    LocalNotificationService.initialize(context);
    //gives us the message on which user taps and it opens the app from terminated state
    //basically means it opens the app when the app is closed
    FirebaseMessaging.instance.getInitialMessage().then((message) {
      if (message != null) {
        final routeFromMessage = message.data["route"];
        Navigator.of(context).pushNamed(routeFromMessage);
      }
    });

    //only works when the app is in foreground
    FirebaseMessaging.onMessage.listen((message) {
      if (message.notification != null) {
        print(message.notification!.body);
        print(message.notification!.title);
      }

      LocalNotificationService.display(message);
    });

    //only works when the app is in background but open and user taps on the notification
    FirebaseMessaging.onMessageOpenedApp.listen((message) {
      final routeFromMessage = message.data["route"];
      Navigator.of(context).pushNamed(routeFromMessage);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("you will receive message"),
              OutlineButton(
                child: Text("Register My Device"),
                onPressed: () {
                  _register();
                },
              ), // Text("Message: $message")
            ]),
      ),
    );
  }
}
//
//
// import 'package:flutter/material.dart';
// import 'package:firebase_messaging/firebase_messaging.dart';
// void main() => runApp(MyApp());
//
// class MyApp extends StatefulWidget {
//   @override State createState() {
//     return _MyAppState();
//   }
// }
//
// class _MyAppState extends State<MyApp> {
//   String _message = '';
//   final FirebaseMessaging _firebaseMessaging = FirebaseMessaging();
//   _register() {
//     _firebaseMessaging.getToken().then((token) => print(token));
//   }
//
// @override void initState() {
//   // TODO: implement initState
//    super.initState();
//    getMessage();
// }
//
// void getMessage(){
//   _firebaseMessaging.configure(
//       onMessage: (Map<String, dynamic> message) async {
//     print('on message $message');
//     setState(() => _message = message["notification"]["title"]);
//     }, onResume: (
//       Map<String, dynamic> message
//       ) async {
//         print('on resume $message');
//         setState(() => _message = message["notification"]["title"]);
//         },
//       onLaunch: (
//           Map<String, dynamic> message
//           ) async {
//         print('on launch $message');
//         setState(() => _message = message["notification"]["title"]);
//       });
// }
// @override Widget build(BuildContext context) {
//   // TODO: implement build
//    return MaterialApp(
//        home: Scaffold(
//          body: Center(
//            child: Column(
//              mainAxisAlignment: MainAxisAlignment.center,
//                children: [
//                  Text("Message: $_message"),
//                OutlineButton(
//                  child: Text("Register My Device"),
//                  onPressed: () {
//                    _register();
//                  },
//                ), // Text("Message: $message")
//              ]),
//            ),
//          ),
//        );
//      }

