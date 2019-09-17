package bbdn.sample.b2rest.model;
 
public class TocEntry {
 
    private String courseId;
    private String label;
    private String url;
    private String id;
 
    // Must have no-argument constructor
    public TocEntry() {
 
    }
 
    public TocEntry(String courseId, String label, String url, String id) {
        this.courseId = courseId;
        this.label = label;
        this.url = url;
        this.id = id;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
 
    public String getCourseId() {
        return this.courseId;
    }
 
    public void setLabel(String label) {
        this.label = label;
    }
 
    public String getLabel() {
        return this.label;
    }
 
    public void setUrl(String url) {
        this.url = url;
    }
 
    public String getUrl() {
        return this.url;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getId() {
        return this.id;
    }
 
    @Override
    public String toString() {
        return new StringBuffer(" Course ID : ").append(this.courseId)
                .append(" Label : ").append(this.label)
                .append(" URL : ").append(this.url)
                .append(" ID : ").append(this.id).toString();
    }
 
}