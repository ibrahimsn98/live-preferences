## LivePreferences
Live shared preferences library for Android

[![](https://jitpack.io/v/ibrahimsn98/live-preferences.svg)](https://jitpack.io/#ibrahimsn98/live-preferences)

## Setup
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
      implementation 'com.github.ibrahimsn98:live-preferences:1.0'
}
```

## Usage
```kotlin
val preferences = PreferenceManager.getDefaultSharedPreferences(this)
val liveSharedPreferences = LiveSharedPreferences(preferences)

liveSharedPreferences.getString("exampleString", "default").observe(this, Observer<String> { value ->
    Log.d(TAG, value)
})

liveSharedPreferences.getInt("exampleInt", 0).observe(this, Observer<Int> { value ->
    Log.d(TAG, value.toString())
})

liveSharedPreferences.getBoolean("exampleBoolean", false).observe(this, Observer<Boolean> { value ->
    Log.d(TAG, value.toString())
})
```

Additionally, you can also observe multiple preferences which are same type in the one Observer object.

```kotlin
liveSharedPreferences.listenMultiple(listOf("bool1", "bool2", "bool3"), false).observe(this, Observer<Pair<String, Boolean>> { value ->
    Log.d(TAG, "key: ${value!!.first} value: ${value.second}")
})
```

License
--------

    MIT License

	Copyright (c) 2018 ibrahim süren

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
	© 2018 Git


> Follow me on Twitter [@ibrahimsn98](https://twitter.com/ibrahimsn98)