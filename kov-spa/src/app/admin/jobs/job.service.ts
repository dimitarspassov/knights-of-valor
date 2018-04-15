import {Injectable} from '@angular/core';
import {HttpService} from '../../core/http.service';
import {AddJobModel} from './add/add-job.model';
import {EditJobModel} from './edit/edit-job.model';


@Injectable()
export class JobService {

  private readonly ADD_JOB_URL = 'api/admin/jobs/add';
  private readonly GET_JOB_URL = 'api/admin/jobs/';
  private readonly EDIT_JOB_IMG_URL = 'api/admin/jobs/newimg/';


  constructor(private httpService: HttpService) {
  }

  addNewJob(job: AddJobModel) {
    const formData = new FormData();
    formData.append('image', job.image, job.image.name);
    formData.append('job', new Blob([JSON.stringify({
      'name': job.name,
      'minutes': job.minutes,
      'salary': job.salary
    })], {
      type: 'application/json'
    }));


    return this.httpService.post(this.ADD_JOB_URL, formData, true, true);
  }

  getJobById(id: string) {
    return this.httpService.get(this.GET_JOB_URL + id, true);
  }


  editJob(id: string, job: EditJobModel) {

    if (job.image !== undefined) {
      const formData = new FormData();
      formData.append('image', job.image, job.image.name);
      formData.append('job', new Blob([JSON.stringify({
        'name': job.name,
        'minutes': job.minutes,
        'salary': job.salary
      })], {
        type: 'application/json'
      }));

      return this.httpService.post(this.EDIT_JOB_IMG_URL + id, formData, true, true);
    } else {
      return this.httpService.post(this.GET_JOB_URL + id, job, true);
    }

  }


  changeJobStatus(jobId: string, status: boolean) {
    return this.httpService.post(`${this.GET_JOB_URL}${jobId}/status/${status}`, null, true);
  }
}
