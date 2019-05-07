package com.example.belief;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.belief.data.db.model.Food;
import com.example.belief.data.db.model.Recipe;
import com.example.belief.data.db.model.RecipeType;
import com.example.belief.data.db.model.SportClass;
import com.example.belief.di.TestComponentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

//FIXME：还是用非测试代码中的DI模块好了
@RunWith(AndroidJUnit4.class)
public class MockPresenterTest {

//    返回正在运行的Context
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());

    @Inject
    MockPresenter mPresenter;

    @Rule
    public TestRule chain = RuleChain.outerRule(component);

    @Before
    public void setUp() {
        component.getTestComponent().inject(this);
//        MockPresenter.mockInsert((MvpApp.get(component.getContext())).getDaoSession());
    }

    @Test
    public void getAllData() {
        mPresenter.getAllData(Food.class);
        mPresenter.getAllData(Recipe.class);
        mPresenter.getAllData(RecipeType.class);
        mPresenter.getAllData(SportClass.class);
    }

    @Test
    public void test1() {
        mPresenter.getJoinedClasses(1);
    }
}