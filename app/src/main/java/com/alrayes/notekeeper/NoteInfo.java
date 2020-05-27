package com.alrayes.notekeeper;

import android.os.Parcel;
import android.os.Parcelable;

public final class NoteInfo implements Parcelable {
    private CourseInfo mCourse;  //everything refrence also must be parcelable
    private String mTitle;
    private String mText;

    public NoteInfo(CourseInfo course, String title, String text) {
        mCourse = course;
        mTitle = title;
        mText = text;
    }

    private NoteInfo(Parcel parcel) {
        // read vaiable back
        //

        // class loader provide information on how to create instance of atype ,
        // cource infot must also be parcelable
        mCourse = parcel.readParcelable(CourseInfo.class.getClassLoader());

        mTitle = parcel.readString();
        mText = parcel.readString();

    }

    public CourseInfo getCourse() {
        return mCourse;
    }

    public void setCourse(CourseInfo course) {
        mCourse = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    private String getCompareKey() {
        return mCourse.getCourseId() + "|" + mTitle + "|" + mText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoteInfo that = (NoteInfo) o;

        return getCompareKey().equals(that.getCompareKey());
    }

    @Override
    public int hashCode() {
        return getCompareKey().hashCode();
    }

    @Override
    public String toString() {
        return getCompareKey();
    }


    //------------- implement parcelable
    /*
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // responsible to write member information for type instance
        // into parcel
        parcel.writeParcelable(mCourse , 0 );
        parcel.writeString(mTitle);
        parcel.writeString(mText);


    }

    public static  final Parcelable.Creator<NoteInfo> CREATOR =
            new Parcelable.Creator<NoteInfo>() {


                @Override
                public NoteInfo createFromParcel(Parcel parcel) {

                    return new NoteInfo(parcel);
                }

                @Override
                public NoteInfo[] newArray(int size) {
                    return new NoteInfo[size];
                }
            };
}
