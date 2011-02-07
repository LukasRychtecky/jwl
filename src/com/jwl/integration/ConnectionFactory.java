package com.jwl.integration;

import com.jwl.business.Environment;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class provides connection to database.
 *
 * @author Lukas Rychtecky
 */
public class ConnectionFactory {
	
	/**
	 * @var watcher's delay is set to 5 minutes
	 */
	private static final int DELAY = 5 * 60 * 1000;
	private static ConnectionFactory instance = null;
	private EntityManagerFactory manager = null;
	private Timer timer = null;

	/**
	 * Returns instance of ConnectionFactory
	 *
	 * @return ConnectionFactory
	 */
	public static ConnectionFactory getInstance() {
		if (ConnectionFactory.instance == null) {
			ConnectionFactory.instance = new ConnectionFactory();
		}
		return ConnectionFactory.instance;
	}

	/**
	 * Returns connection as EntityManagerFactory
	 *
	 * @return EntityManagerFactory
	 */
	public EntityManagerFactory getConnection() {
		if (this.manager == null || !this.manager.isOpen()) {
			this.manager = Persistence.createEntityManagerFactory(Environment.getPersistenceUnit());
		}
		this.watchConnection();
		return this.manager;
	}

	/**
	 * Sets watcher on connection
	 */
	private void watchConnection() {
		if (this.timer != null) {
			this.timer.cancel();
			this.timer = null;
		}
		this.timer = new Timer();
		this.timer.schedule(new Task(), ConnectionFactory.DELAY);
	}

	/**
	 * Kills idle connection
	 */
	private void killConnection() {
		if (this.manager.isOpen()) {
			this.manager.close();
		}
		this.manager = null;
	}

	/**
	 * This class represents connection watcher.
	 */
	private class Task extends TimerTask {

		@Override
		public void run() {
			ConnectionFactory.instance.killConnection();
		}
	}
}
