package com.sanc.app.multiple.thread;

import java.util.ArrayList;
import java.util.List;

import com.sanc.app.model.Student;

public class TestMultipleThread {

	private void handle(Student student) {
		System.out.println(student);
	}

	private void execute() {
		List<Student> studList = new ArrayList<Student>();

		for (int i = 0; i < 100; i++) {
			studList.add(new Student((i + 1), "jerry" + (i + 1)));
		}

		try {
			IExecutor<Student> executor = new IExecutor<Student>() {
				public void execute(Student so) throws Exception {
					handle(so);
				}
			};
			MultipleThreadHelper<Student> multipleThread = new MultipleThreadHelper<Student>(studList, executor);
			multipleThread.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TestMultipleThread thread = new TestMultipleThread();
		thread.execute();
	}
}
