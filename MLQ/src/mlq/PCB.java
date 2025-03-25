package mlq;


public class PCB {
    
    String ProcessID;
    int Priority;
    int ArrivalTime;
    int CPUBurst;
    int StartTime;
    int TerminationTime;
    int TurnAroundTime;
    int WaitingTime;
    int ResponseTime;
    private int remainingTime;

    //public PCB(String ProcessID, int Priority, int ArrivalTime, int CPUBurst, int StartTime, int TerminationTime, int TurnAroundTime, int WaitingTime, int ResponseTime) {
      //  this.ProcessID = ProcessID;
        //this.Priority = Priority;
       // this.ArrivalTime = ArrivalTime;
        //this.CPUBurst = CPUBurst;
        //this.StartTime = StartTime;
        //this.TerminationTime = TerminationTime;
       // this.TurnAroundTime = TurnAroundTime;
       // this.WaitingTime = WaitingTime;
      //  this.ResponseTime = ResponseTime;
      //  this.remainingTime = CPUBurst;
   // }
    
    public PCB(String ProcessID, int Priority, int ArrivalTime, int CPUBurst) {
        this.ProcessID = ProcessID;
        this.Priority = Priority;
        this.ArrivalTime = ArrivalTime;
        this.CPUBurst = CPUBurst;
        this.StartTime = 0;
        this.TerminationTime = 0;
        this.TurnAroundTime = 0;
        this.WaitingTime = 0;
        this.ResponseTime = -1;
        this.remainingTime = CPUBurst;
    }

    public String getProcessID() {
        return ProcessID;
    }

    public int getPriority() {
        return Priority;
    }

    public int getArrivalTime() {
        return ArrivalTime;
    }

    public int getCPUBurst() {
        return CPUBurst;
    }

    public int getStartTime() {
        return StartTime;
    }

    public int getTerminationTime() {
        return TerminationTime;
    }

    public int getTurnAroundTime() {
        return TurnAroundTime;
    }

    public int getWaitingTime() {
        return WaitingTime;
    }

    public int getResponseTime() {
        return ResponseTime;
    }

    public void setProcessID(String ProcessID) {
        this.ProcessID = ProcessID;
    }

    public void setPriority(int Priority) {
        this.Priority = Priority;
    }

    public void setArrivalTime(int ArrivalTime) {
        this.ArrivalTime = ArrivalTime;
    }

    public void setCPUBurst(int CPUBurst) {
        this.CPUBurst = CPUBurst;
    }

    public void setStartTime(int StartTime) {
        this.StartTime = StartTime;
    }

    public void setTerminationTime(int TerminationTime) {
        this.TerminationTime = TerminationTime;
    }

    public void setTurnAroundTime(int TurnAroundTime) {
        this.TurnAroundTime = TurnAroundTime;
    }

    public void setWaitingTime(int WaitingTime) {
        this.WaitingTime = WaitingTime;
    }

    public void setResponseTime(int ResponseTime) {
        this.ResponseTime = ResponseTime;
    }
    
    public int calculateResponseTime() {
        return this.ResponseTime = this.StartTime - this.ArrivalTime;
    }
        
    public int calculateTurnaroundTime() {
        return this.TurnAroundTime = this.TerminationTime - this.ArrivalTime;
    }
    
    public int calculateWaitingTime() {
        return this.WaitingTime = this.TurnAroundTime - this.CPUBurst;
    }
    
    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    @Override
public String toString() {
    return "ProcessID:" + ProcessID +  
            "\n Priority:" + Priority +
            "\n ArrivalTime:" + ArrivalTime +
            "\n CPUBurst:" + CPUBurst +
            "\n StartTime:" + StartTime +
            "\n TerminationTime:" + TerminationTime +
            "\n TurnAroundTime:" + TurnAroundTime +
            "\n WaitingTime:" + WaitingTime +
            "\n ResponseTime:" + ResponseTime;
}


    
}