package com.mhgad.za.vitel.billing.batch;

/**
 *
 * @author plourand
 */
public class SqlConst {
    public static final String RETRIEVE_CDR_RECORDS_SELECT = 
            "   SELECT "
            + "     uniqueid,"
            + "     calldate,"
            + "     clid,"
            + "     src,"
            + "     dst,"
            + "     dcontext,"
            + "     channel,"
            + "     dstchannel,"
            + "     lastapp,"
            + "     lastdata,"
            + "     duration,"
            + "     billsec,"
            + "     disposition,"
            + "     amaflags,"
            + "     accountcode,"
            + "     userfield,"
            + "     cost";
    public static final String RETRIEVE_CDR_RECORDS_FROM = "FROM cdr";
    public static final String RETRIEVE_CDR_RECORDS_WHERE = 
            "   WHERE "
            + "     calldate BETWEEN :start AND :end "
            + "     AND "
            + "     (billsec > 0 AND disposition = 'ANSWERED' "
            + "     AND "
            + "     billsec < 10000 )";
    
    public static final String WRITE_CDR_QUERY =
            "   INSERT INTO cdr("
            + "     uniqueid,"
            + "     calldate,"
            + "     clid,"
            + "     src,"
            + "     dst,"
            + "     dcontext,"
            + "     channel,"
            + "     dstchannel,"
            + "     lastapp,"
            + "     lastdata,"
            + "     duration,"
            + "     billsec,"
            + "     disposition,"
            + "     amaflags,"
            + "     accountcode,"
            + "     userfield,"
            + "     cost "
            + " VALUES( "
            + "     :uniqueid,"
            + "     :callDate,"
            + "     :clid,"
            + "     :src,"
            + "     :dst,"
            + "     :dcontext,"
            + "     :channel,"
            + "     :dstchannel,"
            + "     :lastapp,"
            + "     :lastdata,"
            + "     :duration,"
            + "     :billsec,"
            + "     :disposition,"
            + "     :amaflags,"
            + "     :accountcode,"
            + "     :userfield,"
            + "     :cost)";
}
