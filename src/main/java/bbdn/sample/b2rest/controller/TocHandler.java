package bbdn.sample.b2rest.controller;

import java.util.HashMap;
import java.util.List;

import bbdn.sample.b2rest.model.TocEntry;

import blackboard.data.navigation.CourseToc;
import blackboard.data.ValidationException;
import blackboard.data.course.Course;
import blackboard.persist.navigation.CourseTocDbPersister;
import blackboard.persist.navigation.CourseTocDbLoader;
import blackboard.persist.DataType;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;

import blackboard.platform.coursemenu.CourseTocManagerFactory;
                 
public class TocHandler {

	public static HashMap<Integer,TocEntry> getTocItemById(String tocId) {
		HashMap<Integer,TocEntry> result = new HashMap<Integer,TocEntry>();

		try {
			Id id = Id.generateId(new DataType(CourseToc.class), tocId);

			CourseToc courseToc = CourseTocDbLoader.Default.getInstance().loadById(id);
			
			TocEntry tocEntry = new TocEntry(courseToc.getCourseId().toExternalString(), courseToc.getLabel(), courseToc.getUrl(), courseToc.getId().toExternalString());

			result.put(200,tocEntry);
		}
		catch ( KeyNotFoundException knfe ) {
			result.put(400, new TocEntry());
		} catch ( PersistenceException pe ) {
			result.put(400, new TocEntry());
		} catch ( Exception e ) {
			result.put(400, new TocEntry());
		}
		return result;
	}
   
	public static HashMap<Integer,TocEntry> addTocItem(String courseId, String label, String url) {
		HashMap<Integer,TocEntry> result = new HashMap<Integer,TocEntry>();

		try {
			Id crsId = Id.generateId(new DataType(Course.class), courseId);

			List< CourseToc > tocList = CourseTocDbLoader.Default.getInstance().loadByCourseId( crsId );
			CourseToc ct = CourseTocManagerFactory.getInstance().createExternalLink( crsId, label, true, url );
			
			ct.setPosition( tocList.size() );
			ct.setIsEnabled( true );
			ct.setLaunchInNewWindow( true );
			CourseTocDbPersister.Default.getInstance().persist( ct );

			TocEntry tocEntry = new TocEntry(courseId, label, url, ct.getId().toExternalString());

			result.put(201,tocEntry);
		}
		catch ( KeyNotFoundException knfe ) {
			result.put(400, new TocEntry("KeyNotFoundException", knfe.getMessage(), "", ""));
		} catch ( PersistenceException pe ) {
			result.put(400, new TocEntry("PersistenceException", pe.getMessage(), "", ""));
		} catch ( ValidationException ve ) {
			result.put(400, new TocEntry("ValidationException", ve.getMessage(), "", ""));
		} catch ( Exception e ) {
			result.put(400, new TocEntry("Exception", e.getMessage(), "", ""));
		}
		return result;
	}

	public static HashMap<Integer,TocEntry> updateTocItemById(String sTocId, String courseId, String label, String url) {
		HashMap<Integer,TocEntry> result = new HashMap<Integer,TocEntry>();

		try {
			Id tocId = Id.generateId(new DataType(Course.class), sTocId);
			Id crsId = Id.generateId(new DataType(Course.class), courseId);

			CourseToc courseToc = CourseTocDbLoader.Default.getInstance().loadById(tocId);
			
			courseToc.setCourseId(crsId);
			courseToc.setLabel(label);
			courseToc.setUrl(url);
			
			CourseTocDbPersister.Default.getInstance().persist( courseToc );

			TocEntry tocEntry = new TocEntry(courseToc.getCourseId().toExternalString(), courseToc.getLabel(), courseToc.getUrl(), courseToc.getId().toExternalString());

			result.put(200,tocEntry);
		}
		catch ( KeyNotFoundException knfe ) {
			result.put(400, new TocEntry("KeyNotFoundException", knfe.getMessage(), "", ""));
		} catch ( PersistenceException pe ) {
			result.put(400, new TocEntry("PersistenceException", pe.getMessage(), "", ""));
		} catch ( ValidationException ve ) {
			result.put(400, new TocEntry("ValidationException", ve.getMessage(), "", ""));
		} catch ( Exception e ) {
			result.put(400, new TocEntry("Exception", e.getMessage(), "", ""));
		}
		return result;
	}

	public static HashMap<Integer,TocEntry> deleteTocItemById(String tocId) {
		HashMap<Integer,TocEntry> result = new HashMap<Integer,TocEntry>();

		try {
			Id id = Id.generateId(new DataType(CourseToc.class), tocId);

			CourseTocDbPersister.Default.getInstance().deleteById(id);
			
			TocEntry tocEntry = new TocEntry();

			result.put(204, tocEntry);
		}
		catch ( KeyNotFoundException knfe ) {
			result.put(400, new TocEntry("KeyNotFoundException", knfe.getMessage(), "", ""));
		} catch ( PersistenceException pe ) {
			result.put(400, new TocEntry("PersistenceException", pe.getMessage(), "", ""));
		} catch ( Exception e ) {
			result.put(400, new TocEntry("Exception", e.getMessage(), "", ""));
		}
		return result;
	}
}