package com.bigfoot.tenantmonitor.service;

public interface EmailService {
    String TEXT = """
            <!DOCTYPE html>
            <html>
              <head>
                <meta charset="UTF-8" />
                <title>TenantMonitor Verification Code</title>
              </head>
              <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 40px;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);">
                  <tr>
                    <td style="padding: 30px;">
                      <h2 style="color: #2c3e50;">Welcome to <span style="color: #4CAF50;">TenantMonitor</span> \uD83D\uDC4B</h2>
                      <p style="font-size: 16px; color: #333;">
                        We're excited to have you on board! To complete your sign-up, please use the verification code below:
                      </p>

                      <div style="margin: 30px 0; text-align: center;">
                        <span style="display: inline-block; background-color: #f0f0f0; padding: 15px 25px; font-size: 24px; font-weight: bold; border-radius: 6px; color: #2c3e50;">
                          %s
                        </span>
                      </div>

                      <p style="font-size: 14px; color: #666;">
                        This code is valid for a limited time. If you didn’t request this, you can safely ignore this email.
                      </p>

                      <p style="font-size: 14px; color: #999; margin-top: 40px;">
                        – The TenantMonitor Team
                      </p>
                    </td>
                  </tr>
                </table>
              </body>
            </html>
            """;
    void sendVerificationEmail(final String email, final String verificationCode);
}
