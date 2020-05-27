package com.alrayes.notekeeper;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public final class CourseInfo implements Parcelable {

    /*
        Refrence Type as Intent Extras (make it flattend --> converted to byte )
         1- java serialization
            * supported but not preferred
            * serialization is very runtime expensive
            *  very little coding --> easy to implement --> alot work  do
         2- Parcelable Api
            * much more efficient
            * must be explicitly implemented (harder than serilization)

          3- how to do parclable  (implement parcelable interface)
           1- describe Contents
                * indicates special behaviors
                * generally can return 0

           2- write ToParcel
                * Receives a parcel instance
                * use parcel.write XX to store content
                *

           3- provide public static final CREATOR field

                1- parcelable.Creator implementatoin

                2- parcelable.Creator interface
                    1-CreateFromParcelable
                        *Responsible to create new type instance
                        * receive a parcel instance
                        * use paracel.read XX to access content
                   2- newArray
                        * Receives a size
                        * responsible to create array of type





     */
    private final String mCourseId;
    private final String mTitle;
    private final List<ModuleInfo> mModules;

    public CourseInfo(String courseId, String title, List<ModuleInfo> modules) {
        mCourseId = courseId;
        mTitle = title;
        mModules = modules;
    }

    private CourseInfo(Parcel source) {
        mCourseId = source.readString();
        mTitle = source.readString();
        mModules = new ArrayList<>();
        source.readTypedList(mModules, ModuleInfo.CREATOR);
    }

    public String getCourseId() {
        return mCourseId;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<ModuleInfo> getModules() {
        return mModules;
    }

    public boolean[] getModulesCompletionStatus() {
        boolean[] status = new boolean[mModules.size()];

        for(int i=0; i < mModules.size(); i++)
            status[i] = mModules.get(i).isComplete();

        return status;
    }

    public void setModulesCompletionStatus(boolean[] status) {
        for(int i=0; i < mModules.size(); i++)
            mModules.get(i).setComplete(status[i]);
    }

    public ModuleInfo getModule(String moduleId) {
        for(ModuleInfo moduleInfo: mModules) {
            if(moduleId.equals(moduleInfo.getModuleId()))
                return moduleInfo;
        }
        return null;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseInfo that = (CourseInfo) o;

        return mCourseId.equals(that.mCourseId);

    }

    @Override
    public int hashCode() {
        return mCourseId.hashCode();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCourseId);
        dest.writeString(mTitle);
        dest.writeTypedList(mModules);
    }

    public static final Creator<CourseInfo> CREATOR =
            new Creator<CourseInfo>() {

                @Override
                public CourseInfo createFromParcel(Parcel source) {
                    return new CourseInfo(source);
                }

                @Override
                public CourseInfo[] newArray(int size) {
                    return new CourseInfo[size];
                }
            };

}
