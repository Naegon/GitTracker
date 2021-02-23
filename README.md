# Software Engineering 
### *M1 Android Project J.-F. Lalande*

## 1 Overview
The goal of the project is to reuse most of the components you have implemented in the labs. These components will be used in an application that should do something useful and original. You will have the choice of choosing the use case for your app. It could be a restaurant app, a TV show ap, a sport app, a game navigator app, a social app, etc. This document describes : 
- the required technical elements
- the global features that are expected to be found
- what is expected in your specifications 

## 2 Components to use in your application

### 2.1 Compatibility
Make an application targeting Android 8 (or lower).

### 2.2 Graphical elements
All these elements should be present in your app:
- Button 
- Image
- TextView
- EditText
- At least 2 activities
- A complex ListView (text and image) or a RecyclerView (bonus points !)
- A custom graphical elements not seen in the labs (checkbox, slider, etc.)
- *(optional)* A toolbar on the top right with a menu to access an Activity or a PreferenceActivity.

### 2.3 Network and data processing
Choose an online source of data (not flickr ! another one !). Most of them needs an account to be able to send request (usually you need to generate an access token that you need to include in your app). Process some data from the choosen online source in one of the format:
- JSON
- XML
- other data source format Implement your network requests inside AsyncTasks. 

### 2.4 Security
Use one Android Security permission (additionally to the INTERNET permission). This permission can be for accessing a sensor, an internal information (process, contacts, etc.). Display or use this information in your app. 

### 2.5 Service / Sensors
Design a service that will schedule a repetitive task. The task can be very simple: notification of the user of some new data or data change, clean of some data entered by the user (database deletion, preference reset). Eventually, use a sensor of the phone instead of progaming a service. 

### 2.6 Make user data persistent
Record some inputs entered by the user in your app. You are free to use different types of persistence. Entered data can be some application parameters like notifications, frequencies, content of queries, user’s interests, etc. The more complex the data is, the more points you’ll have (e.g. preferences ≥ sqlite). 

### 2.7 Fragments
One of your activity should use dynamic fragments inside. One possibility is to have two layouts and to switch between layout1 and layout2 and to reuse the same fragment in the two layouts. When hitting a button (or changing a parameter), the application should swith of layout.

## 3 Evaluation 
### 3.1 Criteria
The following indicates the evaluation criteria: 
- Working APK (i.e. running on an Android 6 device): 1 
- Clean and commented source code on github with multiple commits (at least, one at April 6th, one at the end): 1 
- GUI (Image, elements, lists): 2
- Network and data (HTTP, AsyncTask): 3
- Security: 1
- Service / Sensor: 2
- Data persistence (preferences, database, file): 2
- Originality of the use case: 2
- Other extra functionalities: 2
- Fragments: 2
- PDF report: 2 

### 3.2 Submission 
1. Submit your apk in the [moodle](http://www.myefrei.fr) (clean the project, then build it and get the APK file) 
2. Submit the source files of the project (clean the project first, and then zip the directory of the project) 
3. Share the project on github and put the github link in your PDF report and/or in Moodle. 
4. Submit a PDF of two pages max describing your application (main functionalities) 
5. Deadline: see moodle!
