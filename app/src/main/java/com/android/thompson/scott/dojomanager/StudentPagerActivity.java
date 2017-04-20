package com.android.thompson.scott.dojomanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;
import java.util.UUID;

import dojomanager.main.models.Student;
import dojomanager.storage.models.DojoStorageManager;

public class StudentPagerActivity extends AppCompatActivity {
	private static final String EXTRA_STUDENT_ID = "scott.dojomanager.student_id";
	private ViewPager mViewPager;
	private List<Student> mStudents;

	public static Intent newIntent(Context packageContext, UUID studentId) {
		Intent intent = new Intent(packageContext, StudentPagerActivity.class);
		intent.putExtra(EXTRA_STUDENT_ID, studentId);

		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_pager);

		mViewPager = (ViewPager) findViewById(R.id.activity_student_pager_view_pager);

		UUID studentId = (UUID) getIntent().getSerializableExtra(EXTRA_STUDENT_ID);
		mStudents = DojoStorageManager.getInstance().getStudents();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public Fragment getItem(int position) {
				Student student = mStudents.get(position);
				return StudentFragment.newInstance(studentId);
			}

			@Override
			public int getCount() {
				return mStudents.size();
			}
		});

		for(int i=0; i < mStudents.size(); i++) {
			if(mStudents.get(i).getId().equals(studentId)) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}
	}

}