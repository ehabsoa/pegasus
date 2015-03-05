 /**
  *  Copyright 2007-2008 University Of Southern California
  *
  *  Licensed under the Apache License, Version 2.0 (the "License");
  *  you may not use this file except in compliance with the License.
  *  You may obtain a copy of the License at
  *
  *  http://www.apache.org/licenses/LICENSE-2.0
  *
  *  Unless required by applicable law or agreed to in writing,
  *  software distributed under the License is distributed on an "AS IS" BASIS,
  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *  See the License for the specific language governing permissions and
  *  limitations under the License.
 */
package edu.isi.pegasus.planner.classes;



import edu.isi.pegasus.common.util.Currently;
import edu.isi.pegasus.common.util.Version;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
/**
 * Holds the information needed to make one dag file corresponding to a Abstract
 * Dag. It holds information to generate the .dax file which is submitted to
 * Condor.
 *
 *
 * @author Karan Vahi
 * @author Gaurang Mehta
 * @version $Revision$
 */

public class DagInfo extends Data {

    /**
     * The default name for the ADag object, if not supplied in the DAX.
     */
    private static final String DEFAULT_NAME = "PegasusRun";

    /**
     * Vector of String objects containing the jobname_id of jobs making
     * the abstract dag.
     */
//    public Vector dagJobs;

    /**
     * Captures the parent child relations making up the DAG. It is a Vector of
     * <code>PCRelation</code> objects.
     */
 //   public Vector relations;

    /**
     * The name of the Abstract Dag taken from the adag element of the DAX
     * generated by the Abstract Planner.
     */
    private String mNameOfADag;

    /**
     * Refers to the number of the Abstract Dags which are being sent to the
     * Concrete Planner in response to the user's request.
     */
    private String mCount;

    /**
     * Refers to the number of the Dag. Index can vary from 0 to mCount - 1.
     */
    private String mIndex;

    /**
     * It is a unique identifier identifying the concrete DAG generated by Pegasus.
     * It consists of the dag name and the timestamp.
     *
     * @see #mFlowIDName
     * @see #mFlowTimestamp
     */
    private String mFlowID;


    /**
     * It is the name of the dag as generated by Chimera in the dax. If none is
     * specified then a default name of PegasusRun is assigned.
     */
    private String mFlowIDName;

    /**
     * The ISO timestamp corresponding to the time when Pegasus is invoked for a
     * dax. It is used to generate the random directory  names also if required.
     */
    private String mFlowTimestamp;

    /**
     * Keeps the last modified time of the DAX.
     */
    private String mDAXMTime;

    /**
     * Identifies the release version of the VDS software that was
     * used to generate the workflow. It is populated from Version.java.
     *
     */
    private String mReleaseVersion;

    /**
     * The workflow metric objects that contains metrics about the workflow being
     * planned.
     */
    private WorkflowMetrics mWFMetrics;


    /**
     * Contains a unique ordered listing of the logical names referred
     * to by the dag. The TreeMap implementation guarentees us a log(n) execution
     * time for the basic operations. Hence should scale well. The key for the
     * map is the lfn name. The value is a String flag denoting whether this
     * file is an input(i) or output(o) or both (b) or none(n). A value of
     * none(n) would denote an error condition.
     */
    private TreeMap mLFNMap;

    /**
     * The DAX Version
     */
    private String mDAXVersion;


    //for scripts later

    /**
     * The default constructor.
     */
    public DagInfo() {
/*        dagJobs        = new Vector();
        relations      = new Vector();
*/
        mNameOfADag     = "";
        mCount          = "";
        mIndex          = "";
        mFlowID         = "";
        mFlowIDName     = "";
        mFlowTimestamp = "";
        mDAXMTime      = "";
        mReleaseVersion = "";
        mDAXVersion    = "";
        mLFNMap         = new TreeMap();
        mWFMetrics     = new WorkflowMetrics();
    }


    /**
     * Adds a new job to the dag.
     *
     * @param job  the job to be added
     */
/*    public void addNewJob( Job job ) {
        dagJobs.add( job.getID() );
        //increment the various metrics
        mWFMetrics.increment( job );
    }
*/
    
    /**
     * Adds a new PCRelation pair to the Vector of <code>PCRelation</code>
     * pairs. Since we are adding a new relation the isDeleted parameter should
     * be false
     * 
     * @param relation  the relation to be added
     */
 /*   public void addNewRelation(PCRelation relation) {
        relations.addElement( relation );
    }
*/
    /**
     * Adds a new PCRelation pair to the Vector of <code>PCRelation</code>
     * pairs. Since we are adding a new relation the isDeleted parameter should
     * be false.
     *
     * @param parent    The parent in the relation pair
     * @param child     The child in the relation pair
     *
     * @see #relations
     */
 /*   public void addNewRelation(String parent, String child) {
        PCRelation newRelation = new PCRelation(parent, child);
        relations.addElement(newRelation);
    }
*/
    
    /**
     * Adds a new PCRelation pair to the Vector of <code>PCRelation</code> pairs.
     *
     * @param parent    The parent in the relation pair
     * @param child     The child in the relation pair
     * @param isDeleted Whether the relation has been deleted due to the
     *                  reduction algorithm or not
     *
     * @see #relations
     */
/*    public void addNewRelation(String parent, String child, boolean isDeleted) {
        PCRelation newRelation = new PCRelation(parent, child, isDeleted);
        relations.addElement(newRelation);
    }
*/
    
    /**
     * Removes a job from the dag/graph structure. It however does not
     * delete the relations the edges that refer to the job.
     *
     * @param job the job to be removed
     *
     * @return boolean indicating whether removal was successful or not.
     */
/*    public boolean remove( Job  job ){
        mWFMetrics.decrement( job );
        return dagJobs.remove( job.getID() );
    }
*/

    /**
     * It returns the list of lfns referred to by the DAG. The list is unique
 as it is gotten from iterating through the mLFNMap.
     *
     * @return a Set of <code>String<code> objects corresponding to the
     *         logical filenames
     */
    public Set getLFNs(){
        return this.getLFNs( false );
    }

    /**
     * Returns the list of lfns referred to by the DAG. The list is unique
 as it is gotten from iterating through the mLFNMap. The contents of the list
 are determined on the basis of the command line options passed by the user
 at runtime. For e.g. if the user has specified force, then one needs to
 search only for the input files.
     *
     * @param onlyInput  a boolean flag indicating that you need only the input
     *                   files to the whole workflow
     *
     * @return a set of logical filenames.
     */
    public Set getLFNs( boolean onlyInput ) {

        Set lfns = onlyInput ? new HashSet( mLFNMap.size()/3 ):
                                   new HashSet( mLFNMap.size() );
        String key = null;
        String val = null;

        //if the force option is set we
        //need to search only for the
        //input files in the dag i.e
        //whose link is set to input in
        //the dag.
        if ( onlyInput ){
            for (Iterator it = mLFNMap.keySet().iterator(); it.hasNext(); ) {
                key = (String) it.next();
                val = (String) mLFNMap.get(key);

                if ( val.equals( "i" ) ) {
                    lfns.add( key );
                }
            }
        }
        else {
            lfns=new HashSet( mLFNMap.keySet() );
        }

        return lfns;
    }



    /**
     * Returns the label of the workflow, that was specified in the DAX.
     *
     * @return the label of the workflow.
     */
    public String getLabel(){
        return (mNameOfADag == null)?
                this.DEFAULT_NAME:
                mNameOfADag;
    }
    
    /* Returns the mIndex of the workflow, that was specified in the DAX.
     *
     * @return the mIndex of the workflow.
     */
    public String getIndex(){
        return mIndex;
    }
    
    /**
     * Set the index of the workflow, that was specified in the DAX.
     *
     * @param index  the count
     */
    public void setIndex( String index ) {
        mIndex = index;
    }
    
    /**
     * Set the count of the workflow, that was specified in the DAX.
     *
     * @param count  the count
     */
    public void setCount( String count ) {
        mCount = count;
    }
    
    
    /**
     * Returns the count of the workflow, that was specified in the DAX.
     *
     * @return the count
     */
    public String getCount() {
        return this.mCount;
    }
    
    
    /**
     * Returns the dax version
     *
     * @return the dax version.
     */
    public String getDAXVersion(  ) {
        return this.mDAXVersion;
    }


    /**
     * Returns the last modified time for the file containing the workflow
     * description.
     *
     * @return the MTime
     */
    public String getMTime(){
        return mDAXMTime;
    }



    /**
     * Returns the flow timestamp for the workflow.
     *
     * @return the flowtimestamp
     */
    public String getFlowTimestamp(){
        return mFlowTimestamp;
    }

    /**
     * Sets the flow timestamp for the workflow.
     *
     * @param timestamp the flowtimestamp
     */
    public void setFlowTimestamp( String timestamp ){
        mFlowTimestamp = timestamp;
    }



    /**
     * Returns the number of jobs in the dag on the basis of number of elements
     * in the <code>dagJobs</code> Vector.
     *
     * @return the number of the jobs.
     */
/*    public int getNoOfJobs() {
        return dagJobs.size();
    }
*/
    /**
     * Gets all the parents of a particular node.
     *
     * @param node the name of the job whose parents are to be found.
     *
     * @return    Vector corresponding to the parents of the node.
     */
/*    public Vector getParents(String node) {
        //getting the parents of that node
        Enumeration ePcRel = this.relations.elements();
        Vector vParents = new Vector();
        PCRelation currentRelPair;
        while (ePcRel.hasMoreElements()) {
            currentRelPair = (PCRelation) ePcRel.nextElement();
            if (currentRelPair.child.trim().equalsIgnoreCase(node)) {
                vParents.addElement( currentRelPair.parent );
            }
        }

        return vParents;
    }
*/
    /**
     * Get all the children of a particular node.
     *
     * @param node the name of the node whose children we want to find.
     *
     * @return  Vector containing the children of the node.
     */
/*    public Vector getChildren(String node) {
        Enumeration ePcRel = this.relations.elements();
        Vector vChildren = new Vector();
        PCRelation currentRelPair;

        while (ePcRel.hasMoreElements()) {
            currentRelPair = (PCRelation) ePcRel.nextElement();
            if (currentRelPair.parent.trim().equalsIgnoreCase(node)) {
                vChildren.addElement(new String(currentRelPair.child));
            }
        }

        return vChildren;
    }
*/
    /**
     * This returns all the leaf nodes of the dag. The way the structure of Dag
     * is specified in terms of the parent child relationship pairs, the
     * determination of the leaf nodes can be computationally intensive. The
     * complexity if of order n^2.
     *
     * @return Vector of <code>String</code> corresponding to the job names of
     *         the leaf nodes.
     *
     * @see org.griphyn.cPlanner.classes.PCRelation
     * @see org.griphyn.cPlanner.classes.DagInfo#relations
     */
/*    public Vector getLeafNodes() {
        Vector leafJobs = new Vector();
        Vector vJobs = this.dagJobs;
        Vector vRelations = this.relations;
        Enumeration eRel;
        String job;
        PCRelation pcRel;
        boolean isLeaf = false;

        //search for all the jobs which are Roots i.e are not child in relation
        Enumeration e = vJobs.elements();

        while (e.hasMoreElements()) {
            //traverse through all the relations
            job = (String) e.nextElement();
            eRel = vRelations.elements();

            isLeaf = true;
            while (eRel.hasMoreElements()) {
                pcRel = (PCRelation) eRel.nextElement();

                if (pcRel.parent.equalsIgnoreCase(job)) { //means not a Child job
                    isLeaf = false;
                    break;
                }
            }

            //adding if leaf to vector
            if (isLeaf) {
                mLogger.log("Leaf job is " + job, LogManager.DEBUG_MESSAGE_LEVEL);
                leafJobs.addElement( job );
            }
        }

        return leafJobs;
    }
*/
    
    /**
     * It determines the root Nodes for the ADag looking at the relation pairs
     * of the adag. The way the structure of Dag is specified in terms
     * of the parent child relationship pairs, the determination of the leaf
     * nodes can be computationally intensive. The complexity if of
     * order n^2.
     *
     *
     * @return the root jobs of the Adag
     *
     * @see org.griphyn.cPlanner.classes.PCRelation
     * @see org.griphyn.cPlanner.classes.DagInfo#relations
     *
     */
/*
    public Vector getRootNodes() {
        Vector rootJobs = new Vector();
        Vector vJobs = this.dagJobs;
        Vector vRelations = this.relations;
        Enumeration eRel;
        String job;
        PCRelation pcRel;
        boolean isRoot = false;

        //search for all the jobs which are Roots
        //i.e are not child in relation
        Enumeration e = vJobs.elements();

        while (e.hasMoreElements()) {
            //traverse through all the relations
            job = (String) e.nextElement();
            eRel = vRelations.elements();

            isRoot = true;
            while (eRel.hasMoreElements()) {
                pcRel = (PCRelation) eRel.nextElement();

                if (pcRel.child.equalsIgnoreCase(job)) { //means not a Root job
                    isRoot = false;
                    break;
                }
            }
            //adding if Root to vector
            if (isRoot) {
                mLogger.log("Root job is " + job, LogManager.DEBUG_MESSAGE_LEVEL);
                rootJobs.addElement( job );
            }
        }

        return rootJobs;
    }
*/

    /**
     * Returns the workflow metrics so far.
     *
     * @return the workflow metrics
     */
    public WorkflowMetrics getWorkflowMetrics(){
        return this.mWFMetrics;
    }


    /**
     * Generates the flow id for this current run. It is made of the name of the
     * dag and a timestamp. This is a simple concat of the mFlowTimestamp and the
     * flowName. For it work correctly the function needs to be called after the
     * flow name and timestamp have been generated.
     */
    public void generateFlowID() {
        StringBuffer sb = new StringBuffer(40);

        sb.append(mFlowIDName).append("-").append(mFlowTimestamp);

        mFlowID = sb.toString();
    }

    /**
     * Returns the generated flow ID for the workflow.
     *
     * @return
     */
    public String getFlowID(){
        return this.mFlowID;
    }

    /**
     * Generates the name of the flow. 
     */
    public void generateFlowName(){
        StringBuffer sb = new StringBuffer();

        if (mNameOfADag != null)
            sb.append(mNameOfADag);
        else
            sb.append(this.DEFAULT_NAME);

        //append the mCount. that is important for deffered planning
        sb.append("-").append(mIndex);

        mFlowIDName = sb.toString();

    }
    
    /**
     * Returns the flow name
     */
    public String getFlowName(){
        if( mFlowIDName == null ){
            this.generateFlowName();
        }
        return mFlowIDName;

    }

    /**
     * Sets the label for the workflow.
     *
     * @param label the label to be assigned to the workflow
     */
    public void setLabel(String label){
        this.mNameOfADag = label;
        mWFMetrics.setLabel( label );
    }

    /**
     * Sets the dax version
     * @param version   the version of the DAX
     */
    public void setDAXVersion( String version ) {
        mDAXVersion = version;
    }

    /**
     * Sets the mtime (last modified time) for the DAX. It is the time, when
     * the DAX file was last modified. If the DAX file does not exist or an
     * IO error occurs, the MTime is set to OL i.e . The DAX mTime is always
     * generated in an extended format. Generating not in extended format, leads
     * to the XML parser tripping while parsing the invocation record generated
     * by Kickstart.
     *
     * @param f  the file descriptor to the DAX|PDAX file.
     */
    public void setDAXMTime( File f ){
        long time = f.lastModified();
        mDAXMTime = Currently.iso8601(false,true,false,
                                          new java.util.Date(time));
    }


    /**
     * Sets the mtime (last modified time) for the DAX. It is the time, when
     * the DAX file was last modified. If the DAX file does not exist or an
     * IO error occurs, the MTime is set to OL i.e . The DAX mTime is always
     * generated in an extended format. Generating not in extended format, leads
     * to the XML parser tripping while parsing the invocation record generated
     * by Kickstart.
     *
     * @param time  iso formatted time string indicating the last modified time
     *              of DAX
     */
    public void setDAXMTime( String time ){
        mDAXMTime = time;
    }

    /**
     * Return the release version
     * @return 
     */
    public String getReleaseVersion() {
        return this.mReleaseVersion;
    }
    
    /**
     * Grabs the release version from VDS.Properties file.
     *
     * 
     */
    public void setReleaseVersion() {
        this.mReleaseVersion = new Version().getVersion();
    }


    /**
     * Updates the lfn map, that contains the mapping of an lfn with the type.
     *
     * @param lfn  the logical file name.
     * @param type  type the type of lfn (i|o|b). usually a character.
     */
    public void updateLFNMap(String lfn,String type){
        Object entry = mLFNMap.get(lfn);
        if(entry == null){
            mLFNMap.put(lfn,type);
            return;
        }
        else{
            //there is a preexisting entry in the map, check if it needs to be
            //updated
            if(!(entry.equals("b") || entry.equals(type))){
                //types do not match. so upgrade the type to both
                mLFNMap.put(lfn,"b");
            }
        }
    }
    
    /**
     * Returns file counts in a workflow metrics object.
     * 
     * @return 
     */
    public WorkflowMetrics computeDAXFileCounts(){
        int input = 0;
        int inter = 0;
        int output = 0;
        for( Object type: mLFNMap.values()){
            if( type.equals( "i") ){
                input++;
            }
            else if( type.equals( "b" ) ){
                inter++;
            }
            else if( type.equals( "o" ) ){
                output++;
            }
            else{
                throw new RuntimeException( "Invalid type " + type);
            }
        }
        WorkflowMetrics result = new WorkflowMetrics();
        result.setNumDAXFiles(WorkflowMetrics.FILE_TYPE.input, input);
        result.setNumDAXFiles(WorkflowMetrics.FILE_TYPE.intermediate, inter);
        result.setNumDAXFiles(WorkflowMetrics.FILE_TYPE.output, output);
 
        return result;
    }

    /**
     * Returns a new copy of the Object.
     *
     * @return a copy of the object.
     */
    public Object clone() {
        DagInfo dag = new DagInfo();

  //      dag.dagJobs = (Vector)this.dagJobs.clone();
  //      dag.relations = (Vector)this.relations.clone();
        dag.mNameOfADag =  this.mNameOfADag;
        dag.mCount =  this.mCount;
        dag.mIndex =  this.mIndex;
        dag.mDAXVersion = this.mDAXVersion;
        dag.mFlowID = this.mFlowID;
        dag.mFlowIDName     = this.mFlowIDName;
        dag.mFlowTimestamp  = this.mFlowTimestamp;
        dag.mDAXMTime       = this.mDAXMTime;
        dag.mReleaseVersion = this.mReleaseVersion;
        dag.mLFNMap = (TreeMap)this.mLFNMap.clone();
        dag.mWFMetrics = ( WorkflowMetrics )this.mWFMetrics.clone();
        return dag;
    }


    /**
     * Returns the a textual description of the object.
     *
     * @return textual description.
     */
    public String toString() {
        String st = "\n " +
            "\n Name of ADag : " + this.mNameOfADag +
            "\n Index        : " + this.mIndex + " Count :" + this.mCount +
            "\n DAX Version  : " + this.mDAXVersion  +
            //"\n FlowId       : " + this.mFlowID +
            "\n FlowName     : " + this.mFlowIDName +
            "\n FlowTimestamp: " + this.mFlowTimestamp +
            "\n Release Ver  : " + this.mReleaseVersion +
//            vectorToString(" Relations making the Dag ", this.relations) +"\n " +
            "\n Name of ADag : " + this.mNameOfADag +
            "\n Index        : " + this.mIndex + " Count :" + this.mCount +
            "\n DAX Version  : " + this.mDAXVersion  +
            //"\n FlowId       : " + this.mFlowID +
            "\n FlowName     : " + this.mFlowIDName +
            "\n FlowTimestamp: " + this.mFlowTimestamp +
            "\n Release Ver  : " + this.mReleaseVersion +
//            vectorToString(" Relations making the Dag ", this.relations) +
            "\n LFN List is " + this.mLFNMap;


        return st;
    }

    
}
