package com.madzera.happytree.transaction;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TreeTransactionTest.class,
	TreeTransactionAlternativeTest.class,
	TreeTransactionErrorTest.class
})
public class TreeTransactionSuiteTest {
}
