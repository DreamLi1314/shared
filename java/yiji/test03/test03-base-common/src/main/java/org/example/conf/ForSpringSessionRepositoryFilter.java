package org.example.conf;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class ForSpringSessionRepositoryFilter
   extends AbstractHttpSessionApplicationInitializer
{

   public ForSpringSessionRepositoryFilter() {
      super(SessionConfig.class);
   }

}
