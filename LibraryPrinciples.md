

1. For getXOrNull() : it will return the X with all the default considerations in function itself. it will never give any error since its enclosed in try catch
2. For getXOrError(): it won't consider any defaults and all the dependencies are needed to be provided by caller

3. For network, library supports all kinds of interceptors and convertors but gives first class 
   support to stetho/moshi and gson

gson/moshi interoperability
parcelize,
refactoring
waiting:
- hilt base files
- dependency inside dependency




# extensions and hof (Higher order functions)
A task could depend on one or more dependencies. However, somwtimes, it isn't dependent on 2 dependencies but rather one dependency which needs another dependency to get the task X done. for eg :

```text
//https://stackoverflow.com/a/66492857/7500651
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun AppCompatActivity.setSystemStatusBarColor(@ColorRes res: Int) {//todo verify
    val window = this.window ?: return
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = getColorCompat(res)
}

```
here we need a color and we will be able to complete the task. so this is  aperfect example of where we should be making an extensions as TASK<ACTOR<DEPENDENCY. So our extension becomes fun ACTOR.DO_TASK(DEPENDENCY)

however when there are more than one actors, we need to make a higher order function. for eg

```text
fun Context?.showKeyboardForced(view: View? = null) {
    this ?: return
    val finalView = view ?: contextAsView()
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(finalView, InputMethodManager.SHOW_FORCED)
}

```

in here our task is to show keyboard. it requires 2 actors(context , view) to fulfil the task. thus its wrong to have  an extension of a dependency for task on another dependency, when they are both equal in priority.