package com.bignerdranch.andriod.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.UUID;

/**
 * Created by YKC on 2016. 9. 19..
 */
public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;

    private Crime mc;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 액티비티가 운영체제로부터 자신의 콜백 메서드 호출을 받았을 때 프래그먼트의 콜백 매서드가 호출받아야 한다는 것을
        // 명시적으로 FragmentManager에 알려준다
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // 슈퍼클래스에 의해 정의된 어떤 메뉴 기능도 여전히 동작할 수 있도록 함 , 관례적일 뿐 특별한 의미는 없다.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //사용자가 메뉴의 항목을 누르면 콜백 호출받음
        //MenuItem의 ID를 가지고 어떤 메뉴를 눌렀는지 알수있다.
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime: //새로운 범죄 메뉴 클릭했을 경우
                Crime crime = new Crime(); //새로운 Crime 객체를 CrimeLab에 추가
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity // Crime 객체를 수정할수있게 CrimePagerActivity의 인스턴스를 시작시킨다.
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle: //서브타이틀 메뉴 클릭했을 경우
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu(); // onCreateOptionsMenu() 를 호출
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateSubtitle() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        // 서브타이틀 문자열을 생성
        String subtitle = getString(R.string.subtitle_format,crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        // CrimeListFragment를 호스팅하는 액티비티의 타입을 AppCompatActivity로 캐스팅
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    private class CrimeHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {

        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;



        public CrimeHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            // Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            // CrimeActivity에서 CrimePagerActivity로 변경
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            mc = mCrime;
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CRIME);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CRIME) {
            //결과 처리 코드
        }
    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);

            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }




    }


    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(crimes.lastIndexOf(mc));
        }

        updateSubtitle();
    }

}
