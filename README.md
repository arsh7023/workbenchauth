# workbenchauth
Authentication system for the AURIN Workbench

To build with maven:

	export AURIN_DIR='/etc/aurin'
	mvn clean package -Ddeployment=development -Dsystem=ali-dev -Daurin.dir=$AURIN_DIR
