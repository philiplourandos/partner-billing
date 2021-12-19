package com.mhgad.za.vitel.billing.batch.common;

public final class SqlConst {

    public static final String RETRIEVE_CDR_RECORDS_SELECT = 
            """
            SELECT
                uniqueid,
                calldate,
                clid,
                src,
                dst,
                dcontext,
                channel,
                dstchannel,
                lastapp,
                lastdata,
                duration,
                billsec,
                disposition,
                amaflags,
                accountcode,
                userfield,
                cost
            """;
    public static final String RETRIEVE_CDR_RECORDS_FROM = "FROM cdr";
    public static final String RETRIEVE_CDR_RECORDS_WHERE = 
            """
            WHERE
                calldate BETWEEN :start AND :end
                AND
                (billsec > 0 AND disposition = 'ANSWERED'
                AND
                billsec < 10000 )
            """;

    public static final String WRITE_CDR_QUERY =
            """
            INSERT INTO cdr(
                uniqueid,
                calldate,
                clid,
                src,
                dst,
                dcontext,
                channel,
                dstchannel,
                lastapp,
                lastdata,
                duration,
                billsec,
                disposition,
                amaflags,
                accountcode,
                userfield,
                cost,
                site_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public static final String FILE_OUT_CDR_RECORDS_SELECT = 
            """
            SELECT
                r.uniqueid,
                r.calldate,
                r.clid,
                r.src,
                r.dst,
                r.dcontext,
                r.channel,
                r.dstchannel,
                r.lastapp,
                r.lastdata,
                r.duration,
                r.billsec,
                r.disposition,
                r.amaflags,
                r.accountcode,
                r.userfield,
                r.cost 
            FROM
                cdr r,
                partner p,
                site s
            WHERE
                s.name = ?
                AND
                p.site_id = s.id
                AND
                r.accountcode = p.accountcode
                AND
                s.id = r.site_id
                AND
                r.calldate BETWEEN ? AND ?
            ORDER BY
                r.callDate
            """;

    public static final String INSERT_INTO_ASPIVIA = 
            """
            INSERT INTO aspivia(
                Extension,
                Pbx_Date_Time,
                Duration,
                Account_Code,
                Digits,
                Cost,
                Destination,
                Carrier,
                Attribute,
                Site_id)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    
    private SqlConst() {
    }
}
