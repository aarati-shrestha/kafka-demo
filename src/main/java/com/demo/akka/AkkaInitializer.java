package com.demo.akka;

import com.demo.ContextContainer;
import com.demo.config.SpringExtension;

import akka.actor.ActorRef;



public class AkkaInitializer {
	
	private ActorRef actorRef ;
	public AkkaInitializer() {
		SpringExtension ext = ContextContainer.getContext().getBean(SpringExtension.class);
		ext.initialize(ContextContainer.getContext());	
		
		this.actorRef  = AkkaFactory.getActorSystem()
				.actorOf(ext.props("consumerCCActor"),"kafkainit");	
		actorRef.tell("start", null);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		actorRef.tell("s2", null);
//		actorRef.tell("s3", null);
		
	}	
}


