package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.reporting.legacy.xml.LegacyXmlReportGeneratingListener;

import javafx.application.Application;
import javafx.stage.Stage;

import klotski.Main;

public class MainForTest extends Application {
	
	public final static String PATH_TO_SAVE_REPORT="reports";
	
	// esegue i test specificati
	private static void executeTests(ArrayList<Class> testCases) throws IOException {
		PrintWriter out = new PrintWriter(System.out);
		LegacyXmlReportGeneratingListener xmlListener = new LegacyXmlReportGeneratingListener(Paths.get(PATH_TO_SAVE_REPORT), out);
		
		ArrayList<ClassSelector> classes = new ArrayList<ClassSelector>();
		for(int i = 0; i < testCases.size(); i++) {
			ClassSelector toAdd = DiscoverySelectors.selectClass(testCases.get(i));
			classes.add(toAdd);
		}
		
		LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request().selectors(classes).build();
		
		Launcher launcher = LauncherFactory.create();
		TestPlan testPlan = launcher.discover(request);
		launcher.registerTestExecutionListeners(xmlListener);
		launcher.execute(testPlan);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Main obj = new Main();
		obj.start(primaryStage);
		
		// --- aggiungiamo tutti i test da fare
		ArrayList<Class> testCases = new ArrayList<Class>();
		testCases.add(MainTest.class);
		testCases.add(GiocoTest.class);
		testCases.add(PosizioneTest.class);
		testCases.add(ApplicationMenuTest.class);
		testCases.add(MossaTest.class);
		testCases.add(PezzoTest.class);
		testCases.add(GameSceneTest.class);
		testCases.add(ChoiceSceneTest.class);
		testCases.add(HomeSceneTest.class);
		
		
		
		// --- eseguiamo i test
		executeTests(testCases);
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}