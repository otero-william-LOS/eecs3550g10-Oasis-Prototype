/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototype.logic.schedulers;

/**
 *
 * @author jon
 */
public interface Scheduler {    
    /**custom open method to return a single schdlr module instance*/
    public abstract void openModule();
    
    /**custom close method to release/deref any module resources*/
    public abstract void closeModule();
    
    /**custom output method to present meaningful module metrics*/
    public abstract void outputModuleMetrics();
    
    /**custom run method to present a variety of user stories*/
    public abstract void runModuleUserStories();
}
