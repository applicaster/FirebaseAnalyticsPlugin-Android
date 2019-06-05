# Title
Google Analytics for Firebase

# Description
Google's free, deep, and broadly integrated analytics system can help you make more informed business decisions about how to manage your app.

# Image

https://res.cloudinary.com/dtiodujtz/image/upload/v1553522185/Plugins/GAforFirebase.jpg 

# About

*Built with love by the Applicaster Owned Plugins Team*

**Supports:** *Android, iOS*

*Current Version:* 2.2.0 on Android, 7.1.0 on iOS

**About**

Integrated into Google's dominant digital ecosystem, Google Analytics for Firebase (previously just known just as "Firebase Analytics") provides a free, broad, and effective tool for mobile analytics.

One of two analytics solutions recommended by Applicaster as part of your plugin default starter pack (the other being Facebook Analytics), GA for Firebase is the fastest growing analytics SDK in mobile apps, and for good reason. They offer behavioral analysis, user navigation tracking, Google-generated demographic data, acquisition data, and more to help you make more informed business decisions on how to manage your app.

[Click Here](https://firebase.google.com/products/analytics/) to learn more.

In order to use Google Analytics for Firebase, the broader Firebase platform must be configured. [Click Here](https://applicaster.zendesk.com/hc/en-us/articles/115004662546-Firebase-Configuration) to learn learn how to do so. 

**Key Features:**
* Behavioral Analytics - A strong set of [automatic events](https://support.google.com/firebase/answer/6317485?hl=en) complemented by Applicaster's own broad and deep [custom events](https://docs.google.com/spreadsheets/d/1Hlp2sAm9lsKR3x__pk-dD-oCVBO3vJItjnQlrNwB_NM/edit?usp=sharing) gives you a ton of insight into behavior in your app. Can segment by an array of parameters, create user groups based on behavior, and set certain activity up as conversion events to optimize for.
* Navigation Tracking - Powerful funnel analysis and robust screen tracking enables you to understand the navigation patterns in your app in order to better capitalize on the dynamic configurability of our UI Builder
* Broad Integrations - Fully baked into the Google and Firebase ecosystem, you can optimize push messages sent from the Firebase Notifications, capitalize on revenue data from the Google Play Store, track the effectiveness of app marketing campaigns via Google Ads and several other acquisition channels, dump data directly into BigQuery, and more.
* Audience Centric - Capitalizing on Google's enormous data capture and modeling, Firebase enables you to get a strong sense of your users' demographics and interests and even slice behavioral data by this information.
* No data limit on events - As opposed to the traditional Google Analytics platform, there are no data limits on how many events you can send and the platform never resorts to sampling.
* User Data - Enrich your audience analysis by delivering any non-PII (Personally Identifiable Information) user data to the platform, creating user groups based on that information, and segmenting any other analytics feature (e.g. actions, funnels, retention) by these user groups


**Pricing**

Google Analytics for Firebase is completely free.


**Potential Future Features Include:**

* A/B Testing

**Known Limitations**
We did not implement our traditional custom screen view tracking because Firebase contains automatic screen view tracking which cannot be turned off, and sending manual screen views over that created duplicates. Firebase does not provide the ability to override the screen names they collect, which are not always set. As such, we do not recommend this tool for screen view analysis.

Additionally, we send a broad set of custom properties which can be found in the document [here](https://docs.google.com/spreadsheets/d/1Hlp2sAm9lsKR3x__pk-dD-oCVBO3vJItjnQlrNwB_NM/edit?usp=sharing). However, Firebase sets a limit to the number of custom properties you can visualize in the console - 10 text properties and 40 numeric properties. Additionally, you cannot visualize historical data for custom properties, but only from the point of configuration forward. We recommend setting up your custom properties early ([documentation here](https://support.google.com/firebase/answer/7397304?hl=en)). Moreover, if you need insight into additional properties, we recommend complementing this plugin with another provider, or if you have a professional analyst on your team, connecting your Firebase account to BigQuery where all the data can be accessed. 