# Introduction 
This project consists of three separate Kotlin projects:

1. [KotlinMVP](https://github.com/softwarehutpl/KotlinMVP "KotlinMVP") - a *Model View Presenter* design pattern for Android.
2. **[SHRepository](https://github.com/softwarehutpl/SHRepository "SHRepository")** - a *Repository* pattern that does not care whether data comes from disk (for ex. DB) or network. 
3. [MvpPlayground](https://github.com/softwarehutpl/MvpPlayground "MvpPlayground") - A demo of usage of the above projects. Typical Android app with several Activities and Fragments.

## KotlinMVP

**KotlinMVP** is a clean *MVP* design pattern, with focus on `Presenter` not being dependant on either `Activity` or `Fragment`, or any other Android entry level components. 

### Main idea
The main idea is to achieve separation of complex activities/fragments (called here `ParamsActivity` and `ParamsFragment`) and simple ones (`BaseActivity`, `BaseFragment`).

If your Activity is a simple `SplashActivity` which do not have any input parameters, for example lacks an input *id*, then you should extend it by `BaseActivity`.
And if you are now designing your brand new `DetailsActivity` you should use the `ParamsActivity`, because it allows for input *id* to get these details.

#### Params

**Params** is essentially an Android `Bundle` which is stored and restored via Android `savedInstanceState` mechanisms. You only keep here the inputs for `Activity`/`Fragment`. All other stuff is handled and serialized by **SHRepository**.

So your `User` goes to **SHRepository**, and your `userId`, that you used to *GET* the user goes to **Params**.

#### Additional features

1. Decorators. You can now decor your activities with features, without creating new subclasses!
2. Handling on back pressed. There is a versatile support for this.

## SHRepository

**SHRepository** is an advanced *Repository* pattern that does not care whether we are now requesting this `User` from web with our **[Retrofit](http://square.github.io/retrofit/ "Retrofit")**, or if we are doing it from **[Room](https://developer.android.com/topic/libraries/architecture/room "Room")** database. Or any other *REST service* *HTTP client*, database or file system, for that matter.

### Main idea

Imagine that you have to implement an Offline Mode. With **SHRepository** this is a breeze. The library will firstly seek the resources from disk/DB, and if it finds nothing there then and only then it starts  a request to web server. 

All you have to do is to provide implementations for your web data requests via `WebReactiveStorage` interface, and for disk via `ReactiveStorage`. Also, your own `UserRepository` with nicely formatted methods is a good idea! The choice whether the library should get from the DB, or from web is made in background, and the usage looks like this:
```
repository.restoreUser(id).subscribe({
	User user = it //this user might have come from web, or from local DB!
})
```

As you can clearly see, it has **[RxJava](https://github.com/ReactiveX/RxJava "RxJava")** in its core.

Best of all, it stores the web results into DB automatically! Just request this `User` and this object will be ready for your Offline Mode next time you request it.

Examples of how to do the above are inside **MvpPlayground**. Here the `CustomListItem`s are being handled by the **SHRepository**. 

#### Implementation details
##### The contract
The library's contract does not allow for storage in the network. It only stores in database/disk. 

##### What if nothing is there to restore?
If nothing is found on both db/disk, and then on network, then it throws `NoSuchElementException`. 

##### Threading
The library itself operates on default thread. Resort to **RxJava** threading switching to allow your web calls to work on background threads.

## MvpPlayground

This is a project that combines the above and previews the possibilities of that **KotlinMVP** along with **SHRepository**. It is essentially a simple app, which has some data to handle, some screens to show, and some features on top of that. All rely on these two libraries.

### Key points

- The app shows how `Presenter`s from **[KotlinMVP](https://github.com/softwarehutpl/KotlinMVP "KotlinMVP")** are reused. 
- It is also capable of remembering it's own state - recovering custom lists of items, both in activities and fragments. 

#### KotlinMVP in MvpPlayground

You can find implementations of all elements of **KotlinMVP** there:
- `Presenter`s
- `Params` and logic of usage behind them
- Even an `Decorator` from that pattern
- Also how Model looks like there
- Possibly others

#### SHRepository in MvpPlayground

The project handles `CustomListModel`s storage via **SHRepository**. This is done on mocked network side and DB side (courtesy of **Room DB**).

# Getting Started
Both **KotlinMVP** and **SHRepository** are deployed as libraries.

Somewhere in your *build.gradle* file
```
repositories {
    jcenter()
}
```

### SHRepository

```
implementation 'com.softwarehut:SHRepository:1.0.0'
```

### KotlinMVP

```
implementation 'com.softwarehut:KotlinMVP:1.0.0'
```

### MvpPlayground

Just clone the repo!

# Build and Test
**All projects were built in Android Studio.**
The **MvpPlayground** needs actual Android device or Android emulator with min. **SDK version 19**.
