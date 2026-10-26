# Add project specific ProGuard rules here.
# By default, files in this directory are specified with useoptimize/proguard-optimize.txt.

# Retrofit & Gson
-dontwarn retrofit2.**
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Room Database
-keepclassmembers class * extends androidx.room.RoomDatabase {
    *;
}
