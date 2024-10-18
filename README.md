This is a Kotlin Multiplatform project targeting Android, iOS.

* `/drinkfinderapp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

Learn more about [Compose Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-create-first-app.html)…

*Home Screen - Phone*
---
---
![Screenshot_20241016_142620.png](project-screenshot%2FScreenshot_20241016_142620.png)

*Result Screen - (Search for `Vodka` drinks)*
---
---
![vodka.png](project-screenshot%2Fvodka.png)

*Detail Screen*
---
---
![Screenshot_20241016_142907.png](project-screenshot%2FScreenshot_20241016_142907.png)

*Home Screen - Tablet*
---
---
![tablet.png](project-screenshot%2Ftablet.png)




