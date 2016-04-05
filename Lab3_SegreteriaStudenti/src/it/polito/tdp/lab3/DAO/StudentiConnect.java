package it.polito.tdp.lab3.DAO;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	public class StudentiConnect {

		static private final String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root";
		static private StudentiConnect instance = null ;
		
		private StudentiConnect () {
			instance = this ;
			//System.out.println("instance created") ;
		}
		
		public static StudentiConnect getInstance() {
			if(instance == null)
				return new StudentiConnect() ;
			else {
				//System.out.println("instance reused") ;
				return instance ;
			}
		}
		
		public Connection getConnection() {
			try {
				Connection conn = DriverManager.getConnection(jdbcUrl) ;
				return conn ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("Cannot get connection "+jdbcUrl, e) ;
			}	
		}

	}

