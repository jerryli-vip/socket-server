package com.sanc.app.multiple.thread;

public interface IExecutor<E> {

	public void execute(E object) throws Exception;

}