package org.alfresco.repo.web.scripts.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.http.ContentDisposition;

import java.nio.charset.StandardCharsets;

public class EnhancedContentStreamer extends ContentStreamer {

    // Logger
    private static final Log logger = LogFactory.getLog(ContentStreamer.class);

    private static final String HEADER_USER_AGENT = "User-Agent";

    /**
     * Set attachment header including MNT-22668 fix
     * https://github.com/Alfresco/alfresco-community-repo/pull/870/files
     *
     * @param req WebScriptRequest
     * @param res WebScriptResponse
     * @param attach boolean
     * @param attachFileName String
     */
    public void setAttachment(WebScriptRequest req, WebScriptResponse res, boolean attach, String attachFileName)
    {
        if (attach == true)
        {
            String headerValue = "attachment";
            if (attachFileName != null && attachFileName.length() > 0)
            {
                if (logger.isDebugEnabled())
                    logger.debug("Attaching content using filename: " + attachFileName);

                if (req == null)
                {
                    headerValue += "; filename*=UTF-8''" + java.net.URLEncoder.encode(attachFileName, StandardCharsets.UTF_8)
                            + "; filename=\"" + filterNameForQuotedString(attachFileName) + "\"";
                }
                else
                {
                    String userAgent = req.getHeader(HEADER_USER_AGENT);
                    boolean isLegacy = (null != userAgent) && (userAgent.contains("MSIE 8") || userAgent.contains("MSIE 7"));
                    if (isLegacy)
                    {
                        headerValue += "; filename=\"" + java.net.URLEncoder.encode(attachFileName, StandardCharsets.UTF_8);
                    }
                    else
                    {
                        headerValue = ContentDisposition.builder("attachment")
                                .filename(attachFileName, StandardCharsets.UTF_8)
                                .build()
                                .toString();
                    }
                }
            }

            // set header based on filename - will force a Save As from the browse if it doesn't recognize it
            // this is better than the default response of the browser trying to display the contents
            res.setHeader("Content-Disposition", headerValue);
        }
    }

}
